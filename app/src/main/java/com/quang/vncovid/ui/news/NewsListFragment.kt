package com.quang.vncovid.ui.news

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.quang.vncovid.databinding.FragmentNewsDetailBinding

class NewsListFragment(val tab: Int) : Fragment() {

    private val newsViewModel: NewsViewModel by lazy {
        ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (tab) {
            0 -> newsViewModel.getNewsVN()
            1 -> newsViewModel.getNewsWorld()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val newsAdapter = NewsAdapter()
        binding.recyclerView.adapter = newsAdapter
        newsViewModel.newsListVN.observe(viewLifecycleOwner) {
            if (tab == 0) {
                newsAdapter.submitList(it)
            }
        }

        binding.recyclerView.adapter = newsAdapter
        newsViewModel.newsListWorld.observe(viewLifecycleOwner) {
            if (tab == 1) {
                newsAdapter.submitList(it)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}