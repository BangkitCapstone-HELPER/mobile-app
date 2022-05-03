package com.example.helperstartup.Model.Service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    // post login retrofit
//    @FormUrlEncoded
//    @POST("login")
//    fun login(
//        @Field("email") email : String,
//        @Field("password") password: String
//    ) : Call<LoginResponse>
//
//    @FormUrlEncoded
//    @POST("register")
//    fun register(
//        @Field("name") name : String,
//        @Field("email") email: String,
//        @Field("password") password: String
//    ) : Call<SignUpResponse>
//
//    @GET("stories")
//    fun getStories(
//        @Header("Authorization") token: String,
//        @Query("location") location : Int?,
//        @Query("page") page : Int?,
//        @Query("size") size : Int?
//    ) : Call<StoriesResponse>
//
//    @GET("stories")
//    suspend fun getStoriesSuspend(
//        @Header("Authorization") token: String,
//        @Query("location") location : Int?,
//        @Query("page") page : Int?,
//        @Query("size") size : Int?
//    ) : StoriesResponse
//
//
//    @Multipart
//    @POST("stories")
//    fun uploadImage(
//        @Header("Authorization") token: String,
//        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody,
//        @Part("lat") latitude: Double?,
//        @Part("lon") longitude: Double?
//    ): Call<AddStoryResponse>
}