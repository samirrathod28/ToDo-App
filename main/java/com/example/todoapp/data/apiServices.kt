package com.example.todoapp.data

import com.example.todoapp.model.LoginBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices
{
    @FormUrlEncoded
    @POST("/api/login")
    fun insertData(
        @Field("email")email:String,
        @Field("password")password:String
    ): Call<LoginBody?>?
}