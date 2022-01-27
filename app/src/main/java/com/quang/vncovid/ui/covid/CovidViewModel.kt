package com.quang.vncovid.ui.covid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CovidViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is covid Fragment"
    }
    val text: LiveData<String> = _text
}