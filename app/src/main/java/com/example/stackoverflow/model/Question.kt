package com.example.stackoverflow.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Question(
    @Json(name = "has_more")
    val hasMore: Boolean,
    @Json(name = "items")
    val items: List<Item>,
    @Json(name = "quota_max")
    val quotaMax: Int,
    @Json(name = "quota_remaining")
    val quotaRemaining: Int
)