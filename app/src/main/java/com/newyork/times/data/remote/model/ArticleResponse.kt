package com.newyork.times.data.remote.model

import com.google.gson.annotations.SerializedName
import com.newyork.times.app.model.Article


class ArticleResponse{


    @SerializedName("results")
    private var popularArticles: List<Article?>? = null

    /**
     * This method return the list of article entities
     * @return List of entities
     */
    fun getPopularArticles(): List<Article?>? {
        return popularArticles
    }

    /**
     * This method sets the article entities
     * @param popularArticles - articleslist
     */
    fun setPopularArticles(popularArticles: List<Article?>?) {
        this.popularArticles = popularArticles
    }

}