package com.example.mybestvideo.database.interfaces;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/*@Entity(tableName = "video",
        foreignKeys = @ForeignKey(entity = Category.class,
                parentColumns = "id",
                childColumns = "categoryId",
                onDelete = ForeignKey.CASCADE))*/
@Entity(tableName = "video")
public class Video {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "categoryId")
    public int categoryId;

    public Video(String name, String url, int categoryId) {
        this.name = name;
        this.url = url;
        this.categoryId = categoryId;
    }
}