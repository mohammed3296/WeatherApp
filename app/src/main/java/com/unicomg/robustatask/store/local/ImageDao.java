package com.unicomg.robustatask.store.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ImageDao {
    @Insert
    Single<Long> insert(Image image);

    @Query("SELECT * FROM Image ORDER BY `id` DESC")
    Single<List<Image>> getAllImages();

}