package com.example.myapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entities.ToDoEntity;

import java.util.List;

@Dao
public interface ToDoDao {
    @Insert
    void insert(ToDoEntity note);

    @Delete
    void delete(ToDoEntity note);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(ToDoEntity note);

    @Query("SELECT * FROM notes")
    DataSource.Factory<Integer,ToDoEntity> getAll();

    @Query("SELECT * FROM notes WHERE id=:id")
    LiveData<ToDoEntity> getTodo(long id);
}
