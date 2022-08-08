package com.newyork.times.data.remote.repository

import com.newyork.times.data.remote.model.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NetWorkApi {

    @GET("svc/mostpopular/v2/mostviewed/all-sections/{index}.json")
    suspend fun loadPopularArticles(@Path("index") index: Int): Response<ArticleResponse?>?

}