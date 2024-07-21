package com.example.mybestvideo.database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mybestvideo.database.interfaces.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categories")
    List<Category> getAll();

    @Query("SELECT * FROM categories WHERE id = :categoryId LIMIT 1")
    Category findById(int categoryId);

    @Insert
    void insertAll(Category... categories);

    @Delete
    void delete(Category category);
}
