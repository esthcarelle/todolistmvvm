package com.example.myapplication.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.example.myapplication.dao.ToDoDao;
import com.example.myapplication.databases.AppDatabase;
import com.example.myapplication.entities.ToDoEntity;
import com.example.myapplication.models.ToDo;

import java.util.List;

public class ToDoRepository {

    private ToDoDao toDoDao;
    AppDatabase appDatabase;

    private DataSource.Factory<Integer,ToDoEntity> allNotes;
    public ToDoRepository(Application application){
        appDatabase=AppDatabase.getInstance(application);
        toDoDao = appDatabase.toDoDao();
        allNotes = toDoDao.getAll();
    }
    public void insert(ToDoEntity note){
        new InsertAsyncTask(toDoDao).execute(note);
    }

    public void update(ToDoEntity note){
        new UpdateAsyncTask(toDoDao).execute(note);
    }

    public void delete(ToDoEntity note){
        new DeleteAsyncTask(toDoDao).execute(note);
    }


    public DataSource.Factory<Integer,ToDoEntity> getAllNotes(){
        return allNotes;
    }
    private class InsertAsyncTask extends AsyncTask<ToDoEntity,Void,Void> {

        private ToDoDao noteDao;

        InsertAsyncTask(ToDoDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(ToDoEntity... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<ToDoEntity,Void,Void>{

        private ToDoDao noteDao;

        UpdateAsyncTask(ToDoDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(ToDoEntity... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<ToDoEntity,Void,Void>{

        private ToDoDao noteDao;

        DeleteAsyncTask(ToDoDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(ToDoEntity... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }


}
