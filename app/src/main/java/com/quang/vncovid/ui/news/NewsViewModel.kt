package com.quang.vncovid.ui.news

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
    private val _newsListVN = MutableLiveData<List<NewsModel>>()
    val newsListVN: LiveData<List<NewsModel>> = _newsListVN

    private val _newsListWorld = MutableLiveData<List<NewsModel>>()
    val newsListWorld: LiveData<List<NewsModel>> = _newsListWorld

    fun getNewsVN() {
        try {
            viewModelScope.launch {
                _newsListVN.value = apiService.getNews(8).list
            }
        } catch (e: Exception) {

        }

    }

    fun getNewsWorld() {
        try {
            viewModelScope.launch {
                _newsListWorld.value = apiService.getNews(7).list
            }
        } catch (e: Exception) {

        }
    }
}