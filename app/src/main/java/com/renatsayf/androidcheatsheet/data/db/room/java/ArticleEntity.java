package com.renatsayf.androidcheatsheet.data.db.room.java;


import com.renatsayf.androidcheatsheet.App;
import com.renatsayf.androidcheatsheet.R;
import com.renatsayf.androidcheatsheet.models.SimpleItem;

import java.util.ArrayList;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


//region Hint Room_Creating_the_entity
@Entity(tableName = "article")
public class ArticleEntity
{
    @PrimaryKey(autoGenerate = true)
    public int _id;

    @ColumnInfo(name = "header")
    public String header;

    @ColumnInfo(name = "content")
    public String content;

    public static ArrayList<ArticleEntity> getMockData() {

        ArrayList<ArticleEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            ArticleEntity articleEntity = new ArticleEntity();
            articleEntity.header = "Header " + (i + 1);
            articleEntity.content = "Text " + (i + 1) + "....... " + App.Companion.getINSTANCE().getString(R.string.random_text);
            list.add(articleEntity);
        }
        return list;
    }
}
//endregion Room_Creating_the_entity

