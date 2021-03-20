

package com.newyork.times.di

import android.content.Context
import com.newyork.times.app.db.AppDatabase
import com.newyork.times.utils.NetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// this module resolve all the hard android framework dependent objects

@Module
@InstallIn(SingletonComponent::class)
object FrameworkResolver {

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.invoke(context)
    }


    @Singleton
    @Provides
    fun providesNetworkManager(@ApplicationContext context: Context): NetworkManager {
        return NetworkManager(context)
    }
}
