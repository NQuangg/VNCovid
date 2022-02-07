package com.quang.vncovid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.quang.vncovid.databinding.ActivityMainBinding
import com.quang.vncovid.databinding.ActivityNewsDetailBinding

class NewsDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        var newsUrl = intent.getStringExtra("news_url") ?: ""
        newsUrl = newsUrl.replace("https://thanhnien", "https://m.thanhnien")
        binding.webView.loadUrl(newsUrl)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish();
        return true;
    }
}