package com.example.stackoverflow.model

data class Question(
    val view_count: Int,
    val answer_count: Int,
    val title: String,
    val owner: Owner,
    val creation_date: Long
)
