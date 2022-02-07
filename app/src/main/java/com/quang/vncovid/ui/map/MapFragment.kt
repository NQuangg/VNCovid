package com.quang.vncovid.ui.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.quang.vncovid.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Glide.with(binding.root.context).load("https://upload.wikimedia.org/wikipedia/commons/thumb/9/98/COVID-19_Pandemic_Cases_in_Vietnam.svg/800px-COVID-19_Pandemic_Cases_in_Vietnam.svg.png").into(binding.imageView)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}