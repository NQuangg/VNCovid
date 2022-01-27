package com.quang.vncovid

import android.content.res.ColorStateList
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.quang.vncovid.databinding.ActivityMainBinding
import android.text.style.ForegroundColorSpan

import android.text.SpannableString
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.quang.vncovid.ui.covid.CovidFragment
import com.quang.vncovid.ui.home.HomeFragment
import com.quang.vncovid.ui.news.NewsFragment
import com.quang.vncovid.ui.sos.SosFragment
import com.quang.vncovid.ui.statistic.StatisticFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val homeFragment = HomeFragment()
    private val statisticFragment = StatisticFragment()
    private val sosFragment = SosFragment()
    private val covidFragment = CovidFragment()
    private val newsFragment = NewsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView = binding.navView
        val viewPager = binding.viewPager

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 5
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> homeFragment
                    1 -> statisticFragment
                    2 -> sosFragment
                    3 -> covidFragment
                    4 -> newsFragment
                    else -> homeFragment
                }
            }
        }

        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> viewPager.setCurrentItem(0, true)
                R.id.navigation_statistic -> viewPager.setCurrentItem(1, true)
                R.id.navigation_sos -> viewPager.setCurrentItem(2, true)
                R.id.navigation_covid -> viewPager.setCurrentItem(3, true)
                R.id.navigation_news -> viewPager.setCurrentItem(4, true)
            }

            true
        }

        navView.itemIconTintList = null
        val menu = navView.menu
        menu.forEach {
            it.setOnMenuItemClickListener {
                if (it.itemId == R.id.navigation_sos) {
                    menu.getItem(2).title = setSosTitle(R.color.sos_icon)
                } else {
                    menu.getItem(2).title = setSosTitle(R.color.inactive_icon)
                }

                false
            }
        }
    }

    private fun setSosTitle(colorId: Int): SpannableString {
        val string = SpannableString("SOS")
        string.setSpan(ForegroundColorSpan(getColor(colorId)), 0, string.length, 0)
        return string
    }
}