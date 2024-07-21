package com.example.mybestvideo.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mybestvideo.database.Dao.CategoryDao;
import com.example.mybestvideo.database.interfaces.Category;

@Database(entities = {Category.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();
}

