package com.example.mybestvideo.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mybestvideo.database.Dao.VideoDao;
import com.example.mybestvideo.database.interfaces.Video;
import com.example.mybestvideo.database.interfaces.Category;

import java.util.concurrent.Executors;

@Database(entities = {Video.class, Category.class}, version = 1)
public abstract class DBVideo extends RoomDatabase {
    public abstract VideoDao videoDao();

    private static volatile DBVideo INSTANCE;

    public static DBVideo getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DBVideo.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DBVideo.class, "video-database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Insertion des valeurs par défaut
            Executors.newSingleThreadExecutor().execute(() -> {
                VideoDao dao = INSTANCE.videoDao();
                // Insérez ici des valeurs par défaut si nécessaire
            });
        }
    };
}
