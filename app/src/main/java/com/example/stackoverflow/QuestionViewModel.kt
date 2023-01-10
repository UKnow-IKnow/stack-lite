package com.example.stackoverflow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stackoverflow.model.Question
import com.example.stackoverflow.model.QuestionResponse
import com.example.stackoverflow.repository.QuestionRepository
import com.example.stackoverflow.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject internal constructor(private val repository: QuestionRepository): ViewModel() {

    private val _questions = MutableLiveData<Resource<QuestionResponse>>()
    val questions: LiveData<Resource<QuestionResponse>> get() = _questions

    private val _searchedQuestions: MutableLiveData<Resource<QuestionResponse>> =
        MutableLiveData()
    val searchedQuestions: LiveData<Resource<QuestionResponse>> = _searchedQuestions

    private val _taggedQuestions: MutableLiveData<Resource<QuestionResponse>> =
        MutableLiveData()
    val taggedQuestions: LiveData<Resource<QuestionResponse>> = _taggedQuestions

    fun getQuestions() = viewModelScope.launch {
        _questions.postValue(Resource.Loading())
        Log.d("ViewModel", "getActiveQuestions: Status=${_questions.value}")
        val response = repository.getQuestions()
        _questions.postValue(safeHandleResponse(response))
        Log.d("ViewModel", "getActiveQuestions: Status=${questions.value} + ${response.body()}")

    }
    private suspend fun safeHandleResponse(response: Response<QuestionResponse>): Resource<QuestionResponse>? {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(data = response.body()!!)
            } catch (e: Exception) {
                Resource.Error(e.message.toString())
            }
        }
    }
}