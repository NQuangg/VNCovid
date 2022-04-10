package com.quang.vncovid.ui.sos

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import com.quang.vncovid.R
import com.quang.vncovid.databinding.FragmentHomeBinding
import com.quang.vncovid.databinding.FragmentSosBinding
import com.quang.vncovid.ui.home.HomeViewModel
import com.quang.vncovid.ui.statistic.StatisticAdapter
import com.quang.vncovid.ui.statistic.StatisticViewModel

class SosFragment : Fragment() {

    private val sosViewModel: SosViewModel by lazy {
        ViewModelProvider(this).get(SosViewModel::class.java)
    }

    private var _binding: FragmentSosBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sosViewModel.getContacts()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                sosViewModel.searchContacts(p0 ?: "")
                return false
            }
        })


        val sosAdapter = SosAdapter()
        binding.recyclerView.adapter = sosAdapter

        sosViewModel.allContacts.observe(viewLifecycleOwner) {
            sosAdapter.submitList(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}