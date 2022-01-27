package com.quang.vncovid.ui.covid

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.quang.vncovid.R
import com.quang.vncovid.databinding.FragmentCovidBinding
import com.quang.vncovid.databinding.FragmentHomeBinding
import com.quang.vncovid.ui.home.HomeViewModel

class CovidFragment : Fragment() {

    private lateinit var covidViewModel: CovidViewModel
    private var _binding: FragmentCovidBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        covidViewModel =
            ViewModelProvider(this).get(CovidViewModel::class.java)

        _binding = FragmentCovidBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        covidViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}