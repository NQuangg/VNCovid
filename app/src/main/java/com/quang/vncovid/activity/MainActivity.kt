package com.quang.vncovid.activity

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.quang.vncovid.R
import com.quang.vncovid.databinding.ActivityMainBinding
import com.quang.vncovid.main_ui.home.HomeFragment
import com.quang.vncovid.main_ui.map.MapFragment
import com.quang.vncovid.main_ui.news.NewsFragment
import com.quang.vncovid.main_ui.sos.SosFragment
import com.quang.vncovid.main_ui.statistic.StatisticFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val homeFragment = HomeFragment()
    private val statisticFragment = StatisticFragment()
    private val sosFragment = SosFragment()
    private val mapFragment = MapFragment()
//    private val newsFragment = NewsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView = binding.navView
        val viewPager = binding.viewPager

        viewPager.isUserInputEnabled = false
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 5
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> homeFragment
                    1 -> statisticFragment
                    2 -> sosFragment
                    3 -> mapFragment
//                    4 -> newsFragment
                    else -> homeFragment
                }
            }
        }

        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> viewPager.setCurrentItem(0, false)
                R.id.navigation_statistic -> viewPager.setCurrentItem(1, false)
                R.id.navigation_sos -> viewPager.setCurrentItem(2, false)
                R.id.navigation_map -> viewPager.setCurrentItem(3, false)
//                R.id.navigation_news -> viewPager.setCurrentItem(4, false)
            }

            true
        }

        navView.itemIconTintList = null
        val menu = navView.menu
        menu.forEach {
            it.setOnMenuItemClickListener {
                if (it.itemId == R.id.navigation_home) {
                    binding.fab.visibility = View.VISIBLE
                } else {
                    binding.fab.visibility = View.GONE
                }

                if (it.itemId == R.id.navigation_sos) {
                    menu.getItem(2).title = setSosTitle(R.color.sos_icon)
                } else {
                    menu.getItem(2).title = setSosTitle(R.color.inactive_icon)
                }

                false
            }
        }

        binding.fab.setOnClickListener {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser == null) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("phone_number", currentUser.phoneNumber)
                startActivity(intent)
            }
        }
    }

    private fun setSosTitle(colorId: Int): SpannableString {
        val string = SpannableString("SOS")
        string.setSpan(ForegroundColorSpan(getColor(colorId)), 0, string.length, 0)
        return string
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}