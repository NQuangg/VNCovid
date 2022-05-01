package com.quang.vncovid.ui.sos.tab_contact

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.quang.vncovid.data.model.ContactModel
import com.quang.vncovid.databinding.FragmentContactBinding
import com.quang.vncovid.ui.sos.ContactAdapter
import com.quang.vncovid.util.VNCharacterUtils
import kotlinx.coroutines.launch

class ContactFragment: Fragment()  {
    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    private val listContact = mutableListOf<ContactModel>()
    private val contactAdapter = ContactAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val searchView = binding.searchView
        val loading = binding.loading

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(searchString: String?): Boolean {
                val searchListContact = listContact.filter {
                    val provinceString = VNCharacterUtils.convert(it.name)
                    provinceString.contains(searchString ?: "")
                }
                contactAdapter.submitList(searchListContact)
                return false
            }
        })


        binding.recyclerView.adapter = contactAdapter

        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("contact").orderBy("id") .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val contactModel = document.toObject(ContactModel::class.java)
                    listContact.add(contactModel)
                    contactAdapter.submitList(listContact)
                }
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