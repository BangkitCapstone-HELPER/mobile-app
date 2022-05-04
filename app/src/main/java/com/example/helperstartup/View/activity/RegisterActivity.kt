package com.example.helperstartup.View.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.helperstartup.databinding.ActivityLoginBinding
import com.example.helperstartup.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)
    }
}