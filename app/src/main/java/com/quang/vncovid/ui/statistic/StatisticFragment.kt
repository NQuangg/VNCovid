package com.quang.vncovid.ui.statistic

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.quang.vncovid.R
import com.quang.vncovid.databinding.FragmentSosBinding
import com.quang.vncovid.databinding.FragmentStatisticBinding
import com.quang.vncovid.ui.sos.SosViewModel

class StatisticFragment : Fragment() {

    private lateinit var statisticViewModel: StatisticViewModel
    private var _binding: FragmentStatisticBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statisticViewModel =
            ViewModelProvider(this).get(StatisticViewModel::class.java)

        _binding = FragmentStatisticBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        statisticViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}