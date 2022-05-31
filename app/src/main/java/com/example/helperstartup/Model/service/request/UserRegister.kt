package com.example.helperstartup.Model.service.request

data class UserRegister(
    var name: String,
    var email: String,
    var password: String,
    var address: String,
    var phone_number: String
)
