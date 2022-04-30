package com.quang.vncovid.ui.sos.tab_contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quang.vncovid.data.model.ContactModel
import com.quang.vncovid.data.model.listContactModel
import com.quang.vncovid.util.VNCharacterUtils
import kotlinx.coroutines.launch
import java.lang.Exception

class ContactViewModel : ViewModel()  {
    private val _allContacts = MutableLiveData<List<ContactModel>>()
    val allContacts: LiveData<List<ContactModel>> = _allContacts

    fun getContacts() {
        try {
            viewModelScope.launch {
                _allContacts.value = listContactModel
            }
        } catch (e: Exception) {

        }
    }

    fun searchContacts(provinceName: String) {
        val searchString = VNCharacterUtils.convert(provinceName)

        _allContacts.value = listContactModel.filter {
            val provinceString = VNCharacterUtils.convert(it.name)
            provinceString.contains(searchString)
        }
    }
}