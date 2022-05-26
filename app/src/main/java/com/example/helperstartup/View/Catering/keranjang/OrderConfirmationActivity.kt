package com.example.helperstartup.View.Catering.keranjang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.helperstartup.databinding.ActivityOrderConfirmationBinding

class OrderConfirmationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
    }

    private fun setupActionBar() {
        supportActionBar?.title = "Konfirmasi Pesanan"
    }
}