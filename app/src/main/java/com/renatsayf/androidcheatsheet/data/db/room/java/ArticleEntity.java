package com.renatsayf.androidcheatsheet.data.db.room.java;


import com.renatsayf.androidcheatsheet.models.SimpleItem;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


//region Hint Room_Creating the entity
@Entity(tableName = "article")
public class ArticleEntity
{
    @PrimaryKey(autoGenerate = true)
    public int _id;

    @ColumnInfo(name = "header")
    public String header;

    @ColumnInfo(name = "content")
    public String content;
}
//endregion Room_Creating the entity
