package com.unicomg.robustatask.store.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Image.class}, version = 1, exportSchema = false)
public abstract class ImagesDatabase extends RoomDatabase {
    private static ImagesDatabase INSTANCE;
    public static ImagesDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ImagesDatabase.class, "image-database").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract ImageDao imageDao();
}