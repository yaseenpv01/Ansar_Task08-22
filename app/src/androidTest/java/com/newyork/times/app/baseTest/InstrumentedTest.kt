package com.newyork.times.app.baseTest

import com.newyork.times.app.model.Article
import org.junit.Test

/**
Created by Umer Khawaja on 20,March,2021
Dubai, UAE.
 */

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class InstrumentedTest : DatabaseTest(){


    @Test
    fun api_data_insert_success(){

        getCurrentWeatherDao().deleteAllArticle()

        var  currentArticle : ArrayList<Article> =
            ArrayList<Article>()

        var dummyArticle1 = Article()
        var dummyArticle2 = Article()
        var dummyArticle3 = Article()


        currentArticle.add(dummyArticle1)
        currentArticle.add(dummyArticle2)
        currentArticle.add(dummyArticle3)

        getCurrentWeatherDao().insertAll(currentArticle)

        Thread.sleep(2000)

        assert(getCurrentWeatherDao().getSavedArticle().isNotEmpty())

    }


    @Test
    fun api_data_delete_test(){



        var  currentArticle : ArrayList<Article> =
            ArrayList<Article>()

        var dummyArticle1 = Article()
        var dummyArticle2 = Article()
        var dummyArticle3 = Article()


        currentArticle.add(dummyArticle1)
        currentArticle.add(dummyArticle2)
        currentArticle.add(dummyArticle3)

        getCurrentWeatherDao().insertAll(currentArticle)

        Thread.sleep(2000)
        getCurrentWeatherDao().deleteAllArticle()
        Thread.sleep(2000)

        assert(getCurrentWeatherDao().getSavedArticle().isNullOrEmpty())

    }


}