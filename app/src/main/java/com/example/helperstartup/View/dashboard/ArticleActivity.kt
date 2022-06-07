package com.example.helperstartup.View.dashboard

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.helperstartup.Model.data.ArticleModel
import com.example.helperstartup.R


class ArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data = intent.getParcelableExtra<ArticleModel>("DATA")

        val mWebView : WebView = findViewById(R.id.web_view)
        val progresBar : ProgressBar = findViewById(R.id.progresbar)

        mWebView.setVisibility(View.INVISIBLE)
        progresBar.setVisibility(View.VISIBLE)
        mWebView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                progresBar.setVisibility(View.GONE)
                mWebView.setVisibility(View.VISIBLE)
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                Log.d("data", "error")
            }
        })
        mWebView.loadUrl(data?.url.toString())

        val webSettings : WebSettings = mWebView.getSettings()
        webSettings.setJavaScriptEnabled(true)
    }
}