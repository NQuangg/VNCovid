package com.quang.vncovid.main_ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quang.vncovid.data.api.apiService
import com.quang.vncovid.data.model.NewsModel
import kotlinx.coroutines.launch
import java.lang.Exception

class NewsViewModel : ViewModel() {
    private val _newsVNList = MutableLiveData<List<NewsModel>>()
    val newsVNList: LiveData<List<NewsModel>> = _newsVNList

    private val _newsWorldList = MutableLiveData<List<NewsModel>>()
    val newsWorldList: LiveData<List<NewsModel>> = _newsWorldList

    fun getNewsVN() {
        try {
            viewModelScope.launch {
                _newsVNList.value = apiService.getNews(8).list
            }
        } catch (e: Exception) {

        }
    }

    fun getNewsWorld() {
        try {
            viewModelScope.launch {
                _newsWorldList.value = apiService.getNews(7).list
            }
        } catch (e: Exception) {

        }
    }
}