package com.example.helperstartup.Model.Service

import com.example.helperstartup.Model.Service.ResponseApi.ResponseArticle
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("article/")
    fun getArticle(): Call<ResponseArticle>

}