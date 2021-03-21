package com.example.myapplication.entities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "notes")
public class ToDoEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "noteText")
    private String noteText;

    @ColumnInfo(name = "noteDescription")
    private String noteDescription;

    @Ignore
    public ToDoEntity(String noteText, String noteDescription) {
        this.noteText = noteText;
        this.noteDescription = noteDescription;
    }
    public ToDoEntity(long id,String noteText, String noteDescription) {
        this.id = id;
        this.noteText = noteText;
        this.noteDescription = noteDescription;
    }


    public static DiffUtil.ItemCallback<ToDoEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<ToDoEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull ToDoEntity oldItem, @NonNull ToDoEntity newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ToDoEntity oldItem, @NonNull ToDoEntity newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ToDoEntity)) return false;
        ToDoEntity that = (ToDoEntity) o;
        return getId() == that.getId() &&
                Objects.equals(getNoteText(), that.getNoteText()) &&
                Objects.equals(getNoteDescription(), that.getNoteDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNoteText(), getNoteDescription());
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }
}
