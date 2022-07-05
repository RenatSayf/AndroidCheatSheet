package com.renatsayf.androidcheatsheet.domain.db.java;

import com.renatsayf.androidcheatsheet.data.db.room.java.AppDatabase;
import com.renatsayf.androidcheatsheet.data.db.room.java.ArticleDao;
import com.renatsayf.androidcheatsheet.data.db.room.java.ArticleEntity;

import java.util.List;


//region Hint Room_Database_repository
public class DbRepository implements ArticleDao
{
    private final AppDatabase db;

    public DbRepository(AppDatabase db) {
        this.db = db;
    }

    @Override
    public void insertAll(List<ArticleEntity> entities)
    {
        db.articleDao().insertAll(entities);
    }

    @Override
    public List<ArticleEntity> getAll()
    {
        return db.articleDao().getAll();
    }
}
//endregion Room_Database_repository
