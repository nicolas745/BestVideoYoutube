package com.example.mybestvideo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mybestvideo.database.Dao.CategoryDao;
import com.example.mybestvideo.database.interfaces.Category;

import java.util.concurrent.Executors;

@Database(entities = {Category.class}, version = 1)
public abstract class DBcategory extends RoomDatabase {
    public abstract CategoryDao categoryDao();

    private static volatile DBcategory INSTANCE;

    public static DBcategory getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DBcategory.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DBcategory.class, "database-name")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Insertion des valeurs par défaut
            Executors.newSingleThreadExecutor().execute(() -> {
                CategoryDao dao = INSTANCE.categoryDao();
                dao.insertAll(new Category("Default 1"), new Category("Default 2"));
            });
        }
    };
}
