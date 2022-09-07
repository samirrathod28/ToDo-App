package com.example.todoapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginBody{
    @Expose
    @SerializedName("email")
    var email = ""


    @Expose
    @SerializedName("password")
    var password = ""
}