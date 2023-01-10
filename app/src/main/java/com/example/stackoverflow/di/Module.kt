package com.example.stackoverflow.di

import com.example.stackoverflow.api.RetrofitInstance
import com.example.stackoverflow.api.StackExchangeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun providesApiClient(): RetrofitInstance = RetrofitInstance()

    @Provides
    @Singleton
    fun providesStackApi(retrofitInstance: RetrofitInstance): StackExchangeApi =
        retrofitInstance.retrofit.create(StackExchangeApi::class.java)

}