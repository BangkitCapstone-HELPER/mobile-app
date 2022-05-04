package com.example.helperstartup.View.Dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import com.example.helperstartup.Model.Data.ArticleModel
import com.example.helperstartup.R

class ArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data = intent.getParcelableExtra<ArticleModel>("DATA")

        val mWebView : WebView = findViewById(R.id.web_view)
        mWebView.loadUrl(data?.url.toString())

        val webSettings : WebSettings = mWebView.getSettings()
        webSettings.setJavaScriptEnabled(true)
    }
}