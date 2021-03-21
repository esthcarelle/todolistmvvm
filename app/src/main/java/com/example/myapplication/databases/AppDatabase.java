package com.example.myapplication.databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.dao.ToDoDao;
import com.example.myapplication.entities.ToDoEntity;
import com.example.myapplication.models.ToDo;

@Database(entities = {ToDoEntity.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ToDoDao toDoDao();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context,AppDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}