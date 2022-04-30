package com.quang.vncovid.ui.sos.tab_recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quang.vncovid.data.model.ContactModel
import com.quang.vncovid.data.model.RecommendModel
import com.quang.vncovid.data.model.listContactModel
import com.quang.vncovid.data.model.listRecommendModel
import com.quang.vncovid.util.VNCharacterUtils
import kotlinx.coroutines.launch
import java.lang.Exception

class RecommendViewModel : ViewModel()  {
    private val _allRecommend = MutableLiveData<List<RecommendModel>>()
    val allRecommend: LiveData<List<RecommendModel>> = _allRecommend

    fun getRecommends() {
        try {
            viewModelScope.launch {
                _allRecommend.value = listRecommendModel
            }
        } catch (e: Exception) {

        }
    }
}