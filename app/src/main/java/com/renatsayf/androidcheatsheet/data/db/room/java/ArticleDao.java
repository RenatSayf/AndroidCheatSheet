package com.renatsayf.androidcheatsheet.data.db.room.java;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


//region Hint Room_Creating_the_Data_Access_Object
@Dao
public interface ArticleDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ArticleEntity> entities);

    @Query("SELECT * FROM article")
    List<ArticleEntity> getAll();
}
//endregion Room_Creating_the_Data_Access_Object
