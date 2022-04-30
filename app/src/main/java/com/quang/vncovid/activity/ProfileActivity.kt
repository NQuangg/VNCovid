package com.quang.vncovid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.quang.vncovid.R
import com.quang.vncovid.databinding.ActivityLoginBinding
import com.quang.vncovid.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

    }

    override fun onSupportNavigateUp(): Boolean {
        finish();
        return true;
    }
}