package com.example.todoapp.data
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    lateinit var retrofit: Retrofit
    var url="https://reqres.in"

    fun getApiConfig():Retrofit
    {
        retrofit=Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }
}