package com.quang.vncovid.data.api

import com.quang.vncovid.data.model.NewsResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("getArticles")
    suspend fun getNews(@Field("cateId") cateId: Int, @Field("pagination[pageSize]") pageSize: Int = 10): NewsResponse

}