package com.quang.vncovid.ui.sos.tab_recommend

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.quang.vncovid.data.model.ContactModel
import com.quang.vncovid.data.model.RecommendationModel
import com.quang.vncovid.databinding.FragmentRecommendBinding
import com.quang.vncovid.ui.sos.ContactAdapter

class RecommendFragment: Fragment()  {
    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!

    private val listRecommendation = mutableListOf<RecommendationModel>()
    private val recommendAdapter = RecommendAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val loading = binding.loading

        binding.recyclerView.adapter = recommendAdapter

        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("recommendation").orderBy("id") .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val recommendationModel = document.toObject(RecommendationModel::class.java)
                    listRecommendation.add(recommendationModel)
                    recommendAdapter.submitList(listRecommendation)
                }
                recommendAdapter.submitList(listRecommendation)
                loading.visibility = View.GONE
            }
            .addOnFailureListener {
                loading.visibility = View.GONE
                Toast.makeText(requireContext(), "Có lỗi xảy ra.", Toast.LENGTH_SHORT).show()
            }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}