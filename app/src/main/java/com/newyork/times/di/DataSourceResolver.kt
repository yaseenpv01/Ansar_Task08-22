

package com.newyork.times.di

import android.content.Context
import com.newyork.times.app.db.ArticleDao
import com.newyork.times.app.db.ArticleDatabase
import com.newyork.times.data.remote.repository.NetWorkApi
import com.newyork.times.data.remote.repository.Repo
import com.newyork.times.data.remote.repository.RequestInterceptor
import com.newyork.times.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DataSourceResolver {

    @Provides
    @Singleton
    fun provideArticleDao(articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.getArticleDao()
    }


    @Singleton
    @Provides
    fun providesNetworkApi(@ApplicationContext context: Context,okHttpClient : OkHttpClient ): NetWorkApi {

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(NetWorkApi::class.java)
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(
            Constants.CONNECT_TIMEOUT,
            TimeUnit.MILLISECONDS
        )
        okHttpClient.readTimeout(
            Constants.READ_TIMEOUT,
            TimeUnit.MILLISECONDS
        )
        okHttpClient.writeTimeout(
            Constants.WRITE_TIMEOUT,
            TimeUnit.MILLISECONDS
        )
        okHttpClient.addInterceptor(RequestInterceptor())
        okHttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return okHttpClient.build()
    }


    @Provides
    @Singleton
    fun provideArticleRepository(articleDatabase: ArticleDatabase,netWorkApi: NetWorkApi): Repo {
        return Repo(
            articleDatabase,
            netWorkApi
        )
    }
}
