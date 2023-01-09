package com.example.stackoverflow.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Owner(
    @Json(name = "accept_rate")
    val acceptRate: Int,
    @Json(name = "display_name")
    val displayName: String,
    @Json(name = "link")
    val link: String,
    @Json(name = "profile_image")
    val profileImage: String,
    @Json(name = "reputation")
    val reputation: Int,
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "user_type")
    val userType: String
)