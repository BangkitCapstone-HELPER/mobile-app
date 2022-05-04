package com.example.helperstartup.Model.Service

import com.example.helperstartup.Model.Service.ResponseApi.LoginApiResponse
import com.example.helperstartup.Model.Service.ResponseApi.UserInfo
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    // post login retrofit
    @Headers("Content-Type: application/json")
    @POST("user/login")
    fun login(
        @Body userData : UserInfo
    ) : Call<LoginApiResponse>
}