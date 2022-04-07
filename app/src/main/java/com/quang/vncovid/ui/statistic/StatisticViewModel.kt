package com.quang.vncovid.ui.statistic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quang.vncovid.data.api.apiService
import com.quang.vncovid.data.model.ChartCovidModel
import com.quang.vncovid.data.model.ProvinceModel
import com.quang.vncovid.util.VNCharacterUtils
import kotlinx.coroutines.launch
import java.lang.Exception

class StatisticViewModel : ViewModel() {

    private val _allPatientProvinces = MutableLiveData<List<ProvinceModel>>()
    private val _patientProvinces = MutableLiveData<List<ProvinceModel>>()
    val patientProvinces: LiveData<List<ProvinceModel>> = _patientProvinces

    fun getPatientProvinces() {
        try {
            viewModelScope.launch {
                _allPatientProvinces.value = apiService.getAllPatientProvinces().list
                _patientProvinces.value = _allPatientProvinces.value
            }
        } catch (e: Exception) {

        }
    }

    fun searchPatientProvinces(provinceName: String) {
        val searchString = VNCharacterUtils.convert(provinceName)

        _patientProvinces.value = _allPatientProvinces.value?.filter {
            val provinceString = VNCharacterUtils.convert(it.title)
            provinceString.contains(searchString)
        }
    }

}