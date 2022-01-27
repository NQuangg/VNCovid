package com.quang.vncovid.ui.sos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SosViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is sos Fragment"
    }
    val text: LiveData<String> = _text
}