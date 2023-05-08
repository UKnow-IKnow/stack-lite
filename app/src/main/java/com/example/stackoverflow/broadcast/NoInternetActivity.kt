package com.example.stackoverflow.broadcast

import android.os.Bundle
import android.transition.Explode
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.stackoverflow.R

class NoInternetActivity : AppCompatActivity() {

    companion object {
        val TAG = NoInternetActivity::class.simpleName
    }

    var isInternet = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            enterTransition = Explode()
            exitTransition = Explode()
        }
        setContentView(R.layout.no_internet)
        NetworkError(this@NoInternetActivity).observe(this) {
            isInternet = if (!it) {
                // No internet:
                false
            } else {
                // When internet is connected
                finish()
                true
            }
        }

    }
}