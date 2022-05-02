package com.quang.vncovid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.quang.vncovid.databinding.ActivityPhotoBinding
import com.quang.vncovid.fragment.PhotoFragment

class PhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.tvClose.setOnClickListener {
            finish()
        }

        val listImageUrl = intent.getStringArrayListExtra("list_image")
        if (listImageUrl != null) {
            Log.d("QUANGG", listImageUrl.size.toString())

            binding.textPager.text = "1/${listImageUrl.size}"

            val photoViewPager = binding.photoViewPager
            photoViewPager.adapter = object : FragmentStateAdapter(this) {
                override fun getItemCount(): Int {
                    return listImageUrl.size
                }

                override fun createFragment(position: Int): Fragment {
                    return PhotoFragment(imageUrl = listImageUrl[position])
                }
            }

            photoViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.textPager.text = "${position + 1}/${listImageUrl.size}"
                }
            })
        }
    }
}