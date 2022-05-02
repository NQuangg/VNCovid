package com.quang.vncovid.main_ui.sos

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.quang.vncovid.databinding.FragmentSosBinding
import com.quang.vncovid.main_ui.sos.tab_recommend.RecommendFragment
import com.quang.vncovid.main_ui.sos.tab_contact.ContactFragment

class SosFragment : Fragment() {
    private var _binding: FragmentSosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val tabLayout = binding.tabLayout
        val newsViewPager = binding.newsViewPager

        newsViewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 2
            }

            override fun createFragment(position: Int): Fragment {
               return  when (position) {
                    0 -> ContactFragment()
                    else -> RecommendFragment()
                }
            }
        }

        TabLayoutMediator(
            tabLayout, newsViewPager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "Hotline"
                1 -> tab.text = "Khuyến cáo"
            }
        }.attach()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}