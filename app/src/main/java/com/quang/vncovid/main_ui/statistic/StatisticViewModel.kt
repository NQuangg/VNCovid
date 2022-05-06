package com.quang.vncovid.main_ui.statistic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quang.vncovid.data.api.apiService
import com.quang.vncovid.data.model.ProvinceModel
import com.quang.vncovid.util.convertVNCharacters
import kotlinx.coroutines.launch
import java.lang.Exception

class StatisticViewModel : ViewModel() {

    private val _allPatientProvinces = MutableLiveData<List<ProvinceModel>>()
    private val _patientProvinces = MutableLiveData<List<ProvinceModel>>()
    val patientProvinces: LiveData<List<ProvinceModel>> = _patientProvinces

    fun getPatientProvinces() {
        viewModelScope.launch {
            try {
                _allPatientProvinces.value = apiService.getAllPatientProvinces().list
                _patientProvinces.value = _allPatientProvinces.value
            } catch (e: Exception) {

            }
        }

    }

    fun searchPatientProvinces(provinceName: String) {
        val searchString = convertVNCharacters(provinceName)

        _patientProvinces.value = _allPatientProvinces.value?.filter {
            val provinceString = convertVNCharacters(it.title)
            provinceString.contains(searchString)
        }
    }

}