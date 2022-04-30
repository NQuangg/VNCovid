package com.quang.vncovid.ui.sos.tab_contact

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.quang.vncovid.databinding.FragmentContactBinding
import com.quang.vncovid.ui.sos.ContactAdapter

class ContactFragment: Fragment()  {

    private val contactViewModel: ContactViewModel by lazy {
        ViewModelProvider(this).get(ContactViewModel::class.java)
    }

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        contactViewModel.getContacts()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                contactViewModel.searchContacts(p0 ?: "")
                return false
            }
        })


        val contactAdapter = ContactAdapter()
        binding.recyclerView.adapter = contactAdapter

        contactViewModel.allContacts.observe(viewLifecycleOwner) {
            contactAdapter.submitList(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}