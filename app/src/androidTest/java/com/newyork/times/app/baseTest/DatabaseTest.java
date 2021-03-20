package com.newyork.times.app.baseTest;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;


import com.newyork.times.app.db.AppDatabase;
import com.newyork.times.app.db.ArticleDao;

import org.junit.After;
import org.junit.Before;

public abstract class DatabaseTest {

    // system under test
    AppDatabase appDatabase;


    public ArticleDao getCurrentWeatherDao(){
        return appDatabase.getArticleDao();
    }



    @Before
    public void init(){
        try {
            appDatabase = Room.inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext(),
                    AppDatabase.class
            ).build();
        }catch (Exception ex){

            ex.toString();
        }
    }

    @After
    public void finish(){
        appDatabase.close();
    }
}






