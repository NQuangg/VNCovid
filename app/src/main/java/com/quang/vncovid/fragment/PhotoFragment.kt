package com.quang.vncovid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.quang.vncovid.databinding.FragmentPhotoBinding

class PhotoFragment(val imagePath: String): Fragment() {
    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Glide.with(root.context).load("file:///android_asset/$imagePath").into(binding.ivPhoto)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}