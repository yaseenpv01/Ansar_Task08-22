

package com.newyork.times.app.db

import androidx.room.*
import com.newyork.times.app.model.Article
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleDao {

    // insert or update article
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article)


    // insert or update article
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(articles: List<Article?>)


    // get all article from db
    @Query("SELECT * FROM article")
    fun getSavedArticle(): List<Article>

    // delete article from db
    @Delete
    suspend fun deleteArticle(article: Article)

    // delete all article from db
    @Query("DELETE FROM ${Article.TABLE_NAME}")
      fun deleteAllArticle()
}
