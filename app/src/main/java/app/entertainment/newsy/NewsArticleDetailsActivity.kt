package app.entertainment.newsy

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import app.entertainment.newsy.databinding.ActivityNewsArticleDetailsBinding


class NewsArticleDetailsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityNewsArticleDetailsBinding.inflate(layoutInflater) }
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val url = intent.getStringExtra("URL")

        binding.webView.apply {
            settings.javaScriptEnabled = true
//            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            webViewClient = this@NewsArticleDetailsActivity.webViewClient
            loadUrl(url!!)
        }
    }

    private val webViewClient = object : WebViewClient() {
        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            view.loadUrl(url!!)
            return true
        }
    }
}