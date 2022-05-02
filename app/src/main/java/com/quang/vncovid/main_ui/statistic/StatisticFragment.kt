package com.quang.vncovid.main_ui.statistic

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.quang.vncovid.databinding.FragmentStatisticBinding

class StatisticFragment : Fragment() {

    private val statisticViewModel: StatisticViewModel by lazy {
        ViewModelProvider(this).get(StatisticViewModel::class.java)
    }

    private var _binding: FragmentStatisticBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        statisticViewModel.getPatientProvinces()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                statisticViewModel.searchPatientProvinces(p0 ?: "")
                return false
            }
        })

        val statisticAdapter = StatisticAdapter()
        binding.recyclerView.adapter = statisticAdapter

        statisticViewModel.patientProvinces.observe(viewLifecycleOwner) {
            statisticAdapter.submitList(it)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}