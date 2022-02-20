package com.quang.vncovid.ui.map

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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

        Glide.with(binding.root.context)
            .load("https://upload.wikimedia.org/wikipedia/commons/thumb/9/98/COVID-19_Pandemic_Cases_in_Vietnam.svg/800px-COVID-19_Pandemic_Cases_in_Vietnam.svg.png")
            .listener(
                object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.linearLayout.visibility = View.VISIBLE
                        return false
                    }
                }).into(binding.imageView)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}