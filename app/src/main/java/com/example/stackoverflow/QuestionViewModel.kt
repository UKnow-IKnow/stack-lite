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

    init {
        getQuestions()
    }

    fun getQuestions() = viewModelScope.launch {
        _questions.postValue(Resource.Loading())

        val response = repository.getQuestions()
        _questions.postValue(safeHandleResponse(response))


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

    fun searchQuestions(query: String) = viewModelScope.launch {
        _searchedQuestions.postValue(Resource.Loading())
        val response = repository.searchQuestions(query)
        _searchedQuestions.postValue(safeHandleSearchResponse(response))
    }

    private suspend fun safeHandleSearchResponse(response: Response<QuestionResponse>): Resource<QuestionResponse>? {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(data = response.body()!!)
            } catch (e: Exception) {
                Resource.Error(e.message.toString())
            }
        }
    }

    fun searchWithFilter(tags: String) = viewModelScope.launch {
        _taggedQuestions.postValue(Resource.Loading())
        val response = repository.searchWithFilter(tags)
        _taggedQuestions.postValue(safeHandleTaggedResponse(response))
    }

    private suspend fun safeHandleTaggedResponse(response: Response<QuestionResponse>): Resource<QuestionResponse>? {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(data = response.body()!!)
            } catch (e: Exception) {
                Resource.Error(e.message.toString())
            }
        }
    }
}