package com.example.myapplication.fragments;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.activities.MainActivity;
import com.example.myapplication.entities.ToDoEntity;
import com.example.myapplication.viewModels.ToDoViewModel;

public class AddNoteFragment extends DialogFragment {

    //declaration of views and variables
    private EditText noteText;
    private EditText noteDescription;
    private Button addNote;
    String isAddOrModify;
    String id;

    public  AddNoteFragment(){

    }
    public interface AddNoteToLocalDB {
        void onFinishAddNotes(ToDoEntity toDoEntity, String isAddOrModify);
    }


    public static AddNoteFragment newInstance(String title){

        AddNoteFragment frag = new AddNoteFragment();
        Bundle args = new Bundle();
        //add custom title to arguments
        args.putString("title", title);
        frag.setArguments(args);
        return frag;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_to_do, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        noteText = (EditText) view.findViewById(R.id.toDoText);
        noteDescription = (EditText) view.findViewById(R.id.toDoDescription);
        addNote = (Button) view.findViewById(R.id.addNote);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        isAddOrModify= getArguments().getString("isAddOrModify");
        if(isAddOrModify.equals("modify")){
            attachOldDataToEditText();
        }
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        noteText.requestFocus();
        noteDescription.requestFocus();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;

        getDialog().getWindow().setLayout(width,height);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrModifyNote();
            }
        });
    }

    /*method that attaches the note and the description to their editTexts when we want to modify the note*/

    private void attachOldDataToEditText(){
        addNote.setText("Modify note");
        String note = getArguments().getString("note");
        String description = getArguments().getString("description");
        id = getArguments().getString("id");
        noteText.setText(note);
        noteDescription.setText(description);
    }

    /*actual method that add or modifies notes in the local db*/
    private void addOrModifyNote(){
        String noteTextString = noteText.getText().toString();
        String noteDescriptionString = noteDescription.getText().toString();

        //condition to check whether fields are empty or not
        if(noteTextString.trim()!=null && !noteDescriptionString.trim().equals("") && !noteTextString.equals("") && noteDescriptionString.trim()!=null) {


            ToDoEntity toDoEntity = new ToDoEntity(noteTextString,noteDescriptionString);
            AddNoteToLocalDB listener = (AddNoteToLocalDB) getActivity();

            if(isAddOrModify.equals("modify")) {
                toDoEntity.setId(Integer.parseInt(id));
                listener.onFinishAddNotes(toDoEntity, "modify");
            }

            else
                listener.onFinishAddNotes(toDoEntity,"add");




            this.dismiss();
        }
        else
            Toast.makeText(getContext(),"Fill the form correctly",Toast.LENGTH_LONG).show();

    }


}
