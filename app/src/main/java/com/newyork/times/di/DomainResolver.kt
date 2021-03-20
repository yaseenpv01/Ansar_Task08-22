

package com.newyork.times.di

import com.newyork.times.app.db.AppDatabase
import com.newyork.times.app.db.ArticleDatabase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

// this resolver transforms hard android framework dependencies to android free logic objects

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainResolver {

    @Binds
    @Singleton
    abstract fun bindArticleDatabase(appDatabase: AppDatabase): ArticleDatabase


}
