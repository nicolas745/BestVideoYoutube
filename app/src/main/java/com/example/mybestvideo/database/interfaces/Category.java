package com.example.mybestvideo.database.interfaces;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "categorie")
    public String categorie;

    public Category(String categorie) {
        this.categorie = categorie;
    }

    public String getName() {
        return categorie;
    }

    public int getId() {
        return id;
    }
}