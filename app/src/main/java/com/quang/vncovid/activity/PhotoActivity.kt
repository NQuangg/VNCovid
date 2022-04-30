package com.quang.vncovid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.quang.vncovid.R
import com.quang.vncovid.data.model.RecommendModel
import com.quang.vncovid.databinding.ActivityMainBinding
import com.quang.vncovid.databinding.ActivityPhotoBinding
import com.quang.vncovid.fragment.PhotoFragment
import com.quang.vncovid.ui.sos.tab_contact.ContactFragment
import com.quang.vncovid.ui.sos.tab_recommend.RecommendFragment

class PhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.tvClose.setOnClickListener{
            finish()
        }

        val listImage = intent.getStringArrayListExtra("list_image")
        if (listImage != null) {
            Log.d("QUANGG", listImage.size.toString())

            binding.textPager.text = "1/${listImage.size}"

            val photoViewPager =binding.photoViewPager
            photoViewPager.adapter = object : FragmentStateAdapter(this) {
                override fun getItemCount(): Int {
                    return listImage.size
                }

                override fun createFragment(position: Int): Fragment {
                    return  PhotoFragment(imagePath = listImage[position])
                }
            }

            photoViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.textPager.text = "${position+1}/${listImage.size}"
                }
            })
        }
    }
}