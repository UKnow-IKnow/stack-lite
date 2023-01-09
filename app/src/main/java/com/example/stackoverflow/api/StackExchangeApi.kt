package com.example.stackoverflow.api

import com.example.stackoverflow.model.QuestionResponse
import com.example.stackoverflow.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StackExchangeApi {
    @GET("questions")
    suspend fun getQuestions(
        @Query("key") key: String = API_KEY,
        @Query("site") site: String,
        @Query("order") order: String,
        @Query("sort") sort: String
    ): Response<QuestionResponse>
}