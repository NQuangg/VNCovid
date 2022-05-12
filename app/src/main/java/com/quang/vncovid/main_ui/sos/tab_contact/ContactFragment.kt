package com.quang.vncovid.main_ui.sos.tab_contact

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.quang.vncovid.BuildConfig
import com.quang.vncovid.data.model.ContactModel
import com.quang.vncovid.databinding.FragmentContactBinding
import com.quang.vncovid.main_ui.sos.ContactAdapter
import com.quang.vncovid.util.convertVNCharacters

class ContactFragment : Fragment() {
    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    private val listContact = mutableListOf<ContactModel>()
    private lateinit var contactAdapter: ContactAdapter;
    private lateinit var selectedPhoneNumber: String;

    private val callPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel: $selectedPhoneNumber"))
            binding.root.context.startActivity(intent)
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle("Chức năng cần được phép thực hiện cuộc gọi thoại")
                .setNegativeButton(
                    "Hủy"
                ) { _, _ -> }
                .setPositiveButton(
                    "Đồng ý"
                ) { _, _ ->
                    val uri: Uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri)
                    startActivity(intent)
                }
                .create()
                .show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val searchView = binding.searchView
        val loading = binding.loading

        contactAdapter = ContactAdapter(object: ContactAdapter.OnClickItemListener {
            override fun onClickItem(phoneNumber: String) {
                if (ContextCompat.checkSelfPermission(binding.root.context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel: $phoneNumber"))
                    binding.root.context.startActivity(intent)
                } else {
                    selectedPhoneNumber = phoneNumber;
                    callPermissionRequest.launch(Manifest.permission.CALL_PHONE)
                }
            }
        });

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(searchString: String?): Boolean {
                val searchListContact = listContact.filter {
                    val provinceString = convertVNCharacters(it.name)
                    provinceString.contains(searchString ?: "")
                }
                contactAdapter.submitList(searchListContact)
                return false
            }
        })

        binding.recyclerView.adapter = contactAdapter

        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("contact").orderBy("id").get()
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