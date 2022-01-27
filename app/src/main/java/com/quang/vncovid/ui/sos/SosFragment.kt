package com.quang.vncovid.ui.sos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.quang.vncovid.R
import com.quang.vncovid.databinding.FragmentHomeBinding
import com.quang.vncovid.databinding.FragmentSosBinding
import com.quang.vncovid.ui.home.HomeViewModel

class SosFragment : Fragment() {

    private lateinit var sosViewModel: SosViewModel
    private var _binding: FragmentSosBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sosViewModel =
            ViewModelProvider(this).get(SosViewModel::class.java)

        _binding = FragmentSosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        sosViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}