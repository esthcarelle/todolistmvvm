package com.example.myapplication.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.myapplication.dao.ToDoDao;
import com.example.myapplication.entities.ToDoEntity;
import com.example.myapplication.models.ToDo;
import com.example.myapplication.repositories.ToDoRepository;

import java.util.List;

public class ToDoViewModel extends AndroidViewModel {
    private ToDoRepository noteRepository;
    private LiveData<PagedList<ToDoEntity>> allNotes;


    public ToDoViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new ToDoRepository(application);
//        allNotes = noteRepository.getAllNotes();
    }

    public void insert(ToDoEntity note) {
        noteRepository.insert(note);
    }

    public void update(ToDoEntity note) {
        noteRepository.update(note);
    }

    public void delete(ToDoEntity note) {
        noteRepository.delete(note);
    }

    public void init() {
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(true)
                        .setPrefetchDistance(5)
                        .setPageSize(10).build();

        allNotes = (new LivePagedListBuilder(noteRepository.getAllNotes()
                , pagedListConfig))
                .build();


    }

    public LiveData<PagedList<ToDoEntity>> getAllNotes() {
        return allNotes;
    }
}
