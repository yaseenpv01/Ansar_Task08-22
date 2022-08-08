package com.newyork.times.data.remote.repository


import com.newyork.times.app.db.ArticleDatabase
import com.newyork.times.app.model.Article
import com.newyork.times.data.remote.model.ArticleResponse
import com.newyork.times.utils.State
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response


class Repo(private val db: ArticleDatabase,private  val service: NetWorkApi) {

    fun fetchArticles(
        index: Int,

    ): Flow<State<ArticleResponse>> {
        return object : NetworkBoundRepository<ArticleResponse, ArticleResponse>() {

            override suspend fun saveRemoteData(response: ArticleResponse) {

                synchronized(db.getArticleDao()) {

                   db.getArticleDao().deleteAllArticle()
                    response.getPopularArticles()?.let { db.getArticleDao().insertAll(articles = it) }

                }


            }

            override fun fetchFromLocal(): Flow<ArticleResponse>{

                return   flow<ArticleResponse> {

                    try {
                        var tempResponse =
                            ArticleResponse()
                        tempResponse.setPopularArticles(db.getArticleDao().getSavedArticle())
                        emit(tempResponse)
                    } catch (e: Exception) {
                        emit(ArticleResponse())
                    }
                }


            }

            override suspend fun fetchFromRemote(  ): Response<ArticleResponse?>? =
                service.loadPopularArticles(index)

        }.asFlow().flowOn(Dispatchers.IO)
    }


    fun getAllArticlesFromDB(): List<Article> {

        return db.getArticleDao().getSavedArticle()
    }

    fun saveArticlesInDB(list:List<Article>)  {

        db.getArticleDao().insertAll(list)
    }



}


