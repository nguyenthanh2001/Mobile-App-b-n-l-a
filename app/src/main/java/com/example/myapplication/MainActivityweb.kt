package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class MainActivityweb : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activityweb)
        val webview=findViewById<WebView>(R.id.webview)
        webview.loadUrl("https://vietnambiz.vn/gia-gao.html")
    }
}