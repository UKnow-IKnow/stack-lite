package com.example.stackoverflow.broadcast

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.AnyThread
import androidx.annotation.UiThread
import androidx.lifecycle.LiveData

class NetworkError(private val context: Context) : LiveData<Boolean>() {
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback
    private lateinit var networkReceiver: BroadcastReceiver

    init {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                connectivityManagerCallback = object : ConnectivityManager.NetworkCallback() {
                    @AnyThread
                    override fun onCapabilitiesChanged(
                        network: Network,
                        networkCapabilities: NetworkCapabilities
                    ) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && networkCapabilities.hasCapability(
                                NetworkCapabilities.NET_CAPABILITY_VALIDATED
                            )
                        ) {
                            setNewValue(true)
                        }
                    }

                    override fun onAvailable(network: Network) {
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1)
                            setNewValue(true)
                    }

                    override fun onLost(network: Network) {
                        setNewValue(false)
                    }
                }
            }
            else -> {
                networkReceiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context, intent: Intent) {
                        updateConnection()
                    }
                }
            }
        }
    }

    /**this prevents observers to get multiple times the same value*/
    @AnyThread
    private fun setNewValue(isConnected: Boolean) {
        handler.removeCallbacksAndMessages(null)
        if (isUiThread()) {
            if (value != isConnected)
                @SuppressLint("WrongThread")
                value = isConnected
            return
        }
        handler.post {
            if (value != isConnected)
                value = isConnected
        }
    }

    @UiThread
    override fun onActive() {
        super.onActive()
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        updateConnection()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> connectivityManager.registerDefaultNetworkCallback(
                connectivityManagerCallback,
                handler
            )
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> connectivityManager.registerDefaultNetworkCallback(
                connectivityManagerCallback
            )
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                val networkRequest =
                    NetworkRequest.Builder()
                        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build()
                connectivityManager.registerNetworkCallback(
                    networkRequest,
                    connectivityManagerCallback
                )
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                val networkRequest =
                    NetworkRequest.Builder()
                        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build()
                connectivityManager.registerNetworkCallback(
                    networkRequest,
                    connectivityManagerCallback
                )
            }
            else -> {
                @Suppress("DEPRECATION") context.registerReceiver(
                    networkReceiver,
                    IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                )
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val connectivityManager: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
        } else {
            context.unregisterReceiver(networkReceiver)
        }
    }


    @Suppress("DEPRECATION")
    private fun updateConnection() {
        setNewValue(isConnectedToInternet(context))
    }

    companion object {
        @JvmStatic
        fun isUiThread(): Boolean =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) Looper.getMainLooper().isCurrentThread else Thread.currentThread() === Looper.getMainLooper().thread

        @Suppress("DEPRECATION")
        private fun isConnectedToInternet(context: Context): Boolean {
            val connectivityManager: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        }
    }
}