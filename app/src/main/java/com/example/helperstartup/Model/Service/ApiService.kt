package com.example.helperstartup.Model.Service

import com.example.helperstartup.Model.Service.ResponseApi.*
import com.example.helperstartup.Model.Service.request.PasswordChange
import com.example.helperstartup.Model.Service.request.PostTransaction
import com.example.helperstartup.Model.Service.request.ProfileChange
import com.example.helperstartup.Model.Service.request.UserRegister
import okhttp3.MultipartBody
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

    // change password
    @Headers("Content-Type: application/json")
    @POST("user/password/change")
    fun changePassword(
        @Header("Authorization") auth: String,
        @Body passwordData : PasswordChange
    ) : Call<ChangePasswordResponse>

    // patch profile phone Number only
    @Headers("Content-Type: application/json")
    @PATCH("user/")
    fun changeProfile (
        @Header("Authorization") auth: String,
        @Body data : ProfileChange
    ) : Call<ChangeProfileResponse>

    // article
    @GET("article/")
    fun getArticle(): Call<ResponseArticle>

    // menu - home
    @GET("menu/")
    fun getMenu(): Call<ResponseMenu>

    // camera - post
    @Multipart
    @POST("/function-1")
    fun uploadImage(
        @Part file: MultipartBody.Part
    ): Call<ResponseUploadScanner>

    // new Transaction
    @Headers("Content-Type: application/json")
    @POST("transaction/")
    fun postTransaction(
        @Header("Authorization") auth: String,
        @Body data : PostTransaction
    ) : Call<PostTransactionResponse>

    // transaction
    @GET("transaction/")
    fun getTransactions(
        @Header("Authorization") auth: String
    ): Call<TransactionResponse>
}