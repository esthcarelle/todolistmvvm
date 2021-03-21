package com.example.myapplication.models;

public class ToDo {

    private String toDoText;
    private String description;

    public ToDo(String toDoText, String description) {
        this.toDoText = toDoText;
        this.description = description;
    }

    public String getToDoText() {
        return toDoText;
    }

    public void setToDoText(String toDoText) {
        this.toDoText = toDoText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
