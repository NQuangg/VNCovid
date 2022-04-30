package com.quang.vncovid.ui.sos.tab_recommend

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.quang.vncovid.databinding.FragmentRecommendBinding

class RecommendFragment: Fragment()  {

    private val recommendViewModel: RecommendViewModel by lazy {
        ViewModelProvider(this).get(RecommendViewModel::class.java)
    }

    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        recommendViewModel.getRecommends()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val recommendAdapter = RecommendAdapter()
        binding.recyclerView.adapter = recommendAdapter

        recommendViewModel.allRecommend.observe(viewLifecycleOwner) {
            recommendAdapter.submitList(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}