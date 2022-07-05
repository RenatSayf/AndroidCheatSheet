package com.renatsayf.androidcheatsheet.data.db.room.java;


import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


//region Hint Room_Building_the_database
@Database(entities = {ArticleEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract ArticleDao articleDao();

    private static AppDatabase appDb = null;
    public static AppDatabase getInstance(Application app) {

        if (appDb == null)
        {
            appDb = Room.databaseBuilder(app.getApplicationContext(), AppDatabase.class, "app_db")
                    .allowMainThreadQueries()
                    .build();
        } else
        {
           return appDb;
        }
        return appDb;
    }
}
//endregion
