package com.example.helperstartup.Model.Service

import com.example.helperstartup.Model.Service.ResponseApi.LoginApiResponse
import com.example.helperstartup.Model.Service.ResponseApi.RegisterResponse
import com.example.helperstartup.Model.Service.ResponseApi.UserInfo
import com.example.helperstartup.Model.Service.ResponseApi.ResponseArticle
import com.example.helperstartup.Model.Service.request.UserRegister
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    // post login retrofit
    @Headers("Content-Type: application/json")
    @POST("user/login")
    fun login(
        @Body userData : UserInfo
    ) : Call<LoginApiResponse>

    // register
    @Headers("Content-Type: application/json")
    @POST("user/")
    fun register(
        @Body userData : UserRegister
    ) : Call<RegisterResponse>

    @GET("article/")
    fun getArticle(): Call<ResponseArticle>

}