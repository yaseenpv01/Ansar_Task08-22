package com.newyork.times.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.newyork.times.app.model.Article
import com.newyork.times.data.remote.model.converters.RoomObjectConverter


@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = arrayOf(RoomObjectConverter::class))
abstract class AppDatabase : RoomDatabase(), ArticleDatabase {

    abstract override fun getArticleDao(): ArticleDao

    companion object {
        private const val DatabaseName = "articles_db.db"

        @Volatile
        private var instance: AppDatabase? = null

        // Check for DB instance if not null then get or insert or else create new DB Instance
        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            instance ?: createDatabase(context)
                .also { instance = it }
        }

        // create db instance
        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DatabaseName
        ).build()
    }
}

interface ArticleDatabase {
    fun getArticleDao(): ArticleDao
}
