

package com.newyork.times.utils

import android.app.Application
import android.widget.Filter
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.newyork.times.app.model.Article
import com.newyork.times.data.remote.model.ArticleResponse
import com.newyork.times.data.remote.repository.Repo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class ArticleViewModel @ViewModelInject constructor(
    private val repo: Repo,
    private val networkManager: NetworkManager,
) : ViewModel() {

    private val _articles = MutableLiveData<State<ArticleResponse>>()



    val articles: LiveData<State<ArticleResponse>>
        get() = _articles

    val networkObserver = networkManager.observeConnectionStatus

    var index : Int = 7;


    fun getAllNewsFromDB( ) {

        repo.getAllArticlesFromDB()
    }


    fun insertArticleInDB( list : List<Article>) {
        repo.saveArticlesInDB(list)
    }

    fun getNewYorkNews(index: Int) {
        this.index = index
        viewModelScope.launch(IO) {
            repo.fetchArticles(index).collect{
                _articles.postValue(it)
            }
        }
    }

    fun regetNewYorkNews(refreshFailed: () -> Unit = {}) {
        if (networkObserver.value == true) {
            viewModelScope.launch(IO) {
                repo.fetchArticles(index).collect{
                    _articles.postValue(it)
                }
            }
        } else {
            refreshFailed.invoke()
        }
    }




    // set default topic when opening
    private fun <T : Any?> MutableLiveData<T>.defaultTopic(initialValue: T) =
        apply { setValue(initialValue) }
}
