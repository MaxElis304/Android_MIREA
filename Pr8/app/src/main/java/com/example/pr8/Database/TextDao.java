package com.example.pr8.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TextDao {
    @Insert
    void saveText(TextEntity textEntity);

    @Update()
    void updateText(TextEntity textEntity);

    @Query("SELECT * from texts where `key`=(:key)")
    TextEntity getText(String key);
}
