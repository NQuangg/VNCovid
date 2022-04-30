package com.quang.vncovid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.quang.vncovid.R
import com.quang.vncovid.databinding.ActivityDetailReportBinding
import com.quang.vncovid.databinding.ActivityReportBinding

class DetailReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        finish();
        return true;
    }
}