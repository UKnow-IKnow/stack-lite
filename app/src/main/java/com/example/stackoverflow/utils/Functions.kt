package com.example.stackoverflow.utils

import android.content.Context
import android.net.Uri
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import com.example.stackoverflow.model.Question
import java.text.SimpleDateFormat
import java.util.*

fun TextView.getTime(time: Long) {
    val date = Date(time * 1000L)
    val timeFormat = SimpleDateFormat("dd-MM-yyyy").format(date)
    this.text = timeFormat
}

fun Fragment.openQuestion(questionItem: Question, context: Context) {
    val customTabsIntent = CustomTabsIntent.Builder()
        .setShowTitle(true)
        .build()

    customTabsIntent.launchUrl(context, Uri.parse(questionItem.link))
}