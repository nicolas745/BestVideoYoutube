package com.example.mybestvideo.database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mybestvideo.database.interfaces.Video;

import java.util.List;

@Dao
public interface VideoDao {
    @Insert
    void insertAll(Video... videos);
    @Query("SELECT * FROM video WHERE id = :id")
    LiveData<Video> selectById(int id);

    @Query("SELECT * FROM video")
    List<Video> getAllVideos();

    @Query("SELECT * FROM video WHERE categoryId = :categoryId")
    List<Video> getVideosByCategoryId(int categoryId);
}
