package com.quang.vncovid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.quang.vncovid.R
import com.quang.vncovid.databinding.ActivityProfileBinding
import com.quang.vncovid.databinding.ActivityReportBinding

class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val loading = binding.componentLoading.loading

    }

    override fun onSupportNavigateUp(): Boolean {
        finish();
        return true;
    }
}