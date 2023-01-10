package com.example.stackoverflow.model

data class Owner(
    val accept_rate: Int,
    val profile_image: String,
    val display_name: String,
    val link: String,
    val reputation: Int,
    val user_id: Int,
    val user_type: String
)
