package com.example.stackoverflow.repository

import com.example.stackoverflow.api.RetrofitInstance
import com.example.stackoverflow.api.StackExchangeApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val stackExchangeApi: StackExchangeApi) {

    suspend fun getQuestions() = stackExchangeApi.getQuestions()

    suspend fun searchQuestions(query: String) = stackExchangeApi.searchQuestions(query = query)

    suspend fun searchWithFilter(tags: String) = stackExchangeApi.searchWithFilter(tags = tags)

}