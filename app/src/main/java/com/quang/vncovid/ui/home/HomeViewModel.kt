package com.quang.vncovid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quang.vncovid.data.api.apiService
import com.quang.vncovid.data.model.SumPatientModel
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel : ViewModel() {

    private val _sumPatient = MutableLiveData<SumPatientModel>()
    val sumPatient: LiveData<SumPatientModel> = _sumPatient

    fun getSumPatient() {
        try {
            viewModelScope.launch {
                _sumPatient.value = apiService.getSummPatient().data
            }
        } catch (e: Exception) {

        }
    }
}