package com.quang.vncovid.main_ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quang.vncovid.data.api.apiService
import com.quang.vncovid.data.model.ChartCovidModel
import com.quang.vncovid.data.model.SumPatientModel
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel : ViewModel() {

    private val _sumPatient = MutableLiveData<SumPatientModel>()
    val sumPatient: LiveData<SumPatientModel> = _sumPatient

    private val _chartCovidList = MutableLiveData<List<ChartCovidModel>>()
    val chartCovidList: LiveData<List<ChartCovidModel>> = _chartCovidList

    fun getSumPatient() {
        viewModelScope.launch {
            try {
                _sumPatient.value = apiService.getSummPatient().data
            } catch (e: Exception) {

            }
        }
    }

    fun getChartCovid() {
        viewModelScope.launch {
            try {
                _chartCovidList.value = apiService.getChartCovid().list
            } catch (e: Exception) {

            }
        }
    }
}