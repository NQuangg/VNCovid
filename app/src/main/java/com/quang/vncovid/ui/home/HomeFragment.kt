package com.quang.vncovid.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.quang.vncovid.R
import com.quang.vncovid.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeViewModel.getSumPatient()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.sumPatient.observe(viewLifecycleOwner) {
            binding.tvConfirm.text = it.confirmed.toString()
            binding.tvPlusConfirm.text = it.plusConfirmed.toString()

            binding.tvCuring.text = (it.confirmed - it.recovered - it.death).toString()
            binding.tvPlusConfirm.text = (it.plusConfirmed - it.plusRecovered - it.plusDeath).toString()

            binding.tvRecovered.text = it.recovered.toString()
            binding.tvPlusRecovered.text = it.plusRecovered.toString()

            binding.tvDeath.text = it.death.toString()
            binding.tvPlusDeath.text = it.plusDeath.toString()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}