package com.quang.vncovid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.quang.vncovid.R
import com.quang.vncovid.databinding.FragmentPhotoBinding

class PhotoFragment(val imageUrl: String) : Fragment() {
    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val progressDrawable = CircularProgressDrawable(requireContext())
        progressDrawable.strokeWidth = 5f
        progressDrawable.centerRadius = 30f
        progressDrawable.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.white))
        progressDrawable.start()

        Glide.with(root.context)
            .load("https://firebasestorage.googleapis.com/v0/b/vncovid.appspot.com/o$imageUrl?alt=media")
            .placeholder(progressDrawable)
            .into(binding.photoView)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}