package com.example.helperstartup.Model.service.request

data class PasswordChange(
    var new_password : String,
    var old_password : String,
)
