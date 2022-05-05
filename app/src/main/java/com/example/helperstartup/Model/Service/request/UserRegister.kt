package com.example.helperstartup.Model.Service.request

data class UserRegister(
    var name: String,
    var email: String,
    var password: String,
    var address: String,
    var phoneNumber: String
)
