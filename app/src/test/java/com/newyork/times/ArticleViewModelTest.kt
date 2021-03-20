package com.newyork.times

import androidx.lifecycle.Observer
import com.android.weatherforecast.util.RxSchedulerExtensionForJunit5
import com.newyork.times.app.model.Article
import com.newyork.times.data.remote.model.ArticleResponse
import com.newyork.times.data.remote.repository.Repo
import com.newyork.times.util.InstantExecutorExtension
import com.newyork.times.utils.ArticleViewModel
import com.newyork.times.utils.NetworkManager
import com.newyork.times.utils.State
import io.reactivex.Observable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.Context
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.Exception

/**
Created by Umer Khawaja on 20,March,2021
Dubai, UAE.
 */


@ExtendWith(InstantExecutorExtension::class)
class ArticleViewModelTest {


    @Mock
    lateinit var articleRepository: Repo

    @Mock
    lateinit var networkManager: NetworkManager

    lateinit var articleViewModel: ArticleViewModel


    // Test rule for making the RxJava to run synchronously in unit test
    companion object {
        @RegisterExtension
        @JvmField
        val schedulers = RxSchedulerExtensionForJunit5()
    }

    @Mock
    lateinit var articleList: State<ArticleResponse>

    @Mock
    private lateinit var mockContext: Context


    @Mock
    lateinit var observer: Observer<State<ArticleResponse>>

    //Mockito.spy returns real object
    @BeforeEach
    fun setup() {

        try {
            MockitoAnnotations.initMocks(this)
            articleViewModel = Mockito.spy(ArticleViewModel(articleRepository,networkManager))
        }catch (ex: Exception){
            ex.toString()
        }
    }


    @Test
    fun get_Weather_from_db() {

        var  articleList = mutableListOf<Article>()

        var article1 = Mockito.mock(Article::class.java)
        var article2 = Mockito.mock(Article::class.java)
        var article3 = Mockito.mock(Article::class.java)

        articleList.add(article1)
        articleList.add(article2)
        articleList.add(article3)
        articleViewModel.insertArticleInDB(articleList)
        Mockito.`when`(articleRepository.getAllArticlesFromDB())
            .thenReturn(articleList)

        articleViewModel.articles.observeForever(observer)

        articleViewModel.getAllNewsFromDB()

        Thread.sleep(1000)

        assert(articleViewModel.articles.value == State.success(articleList))


    }


    @Test
    fun get_Weather_from_remote() {

        var  articleList: ArrayList<Article> =
            ArrayList<Article>()

        var article1 = Mockito.mock(Article::class.java)
        var article2 = Mockito.mock(Article::class.java)
        var article3 = Mockito.mock(Article::class.java)

        articleList.add(article1)
        articleList.add(article2)
        articleList.add(article3)

        Mockito.`when`(articleRepository.getAllArticlesFromDB())
            .thenReturn(articleList)


        articleViewModel.getNewYorkNews(7)
        Thread.sleep(2000)
        assert(articleViewModel.articles.value == State.success(articleList))

    }


}