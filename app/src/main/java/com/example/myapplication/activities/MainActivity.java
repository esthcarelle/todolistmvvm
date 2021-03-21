package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapters.NotesAdapter;
import com.example.myapplication.entities.ToDoEntity;
import com.example.myapplication.fragments.AddNoteFragment;
import com.example.myapplication.helpers.SwipeHelper;
import com.example.myapplication.helpers.SwipeHelperLeftRight;
import com.example.myapplication.helpers.SwipeHelperRight;
import com.example.myapplication.viewModels.ToDoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AddNoteFragment.AddNoteToLocalDB{


    private ToDoViewModel noteViewModel;
    private NotesAdapter notesAdapter;
    private FloatingActionButton addNote;
    private RecyclerView notesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing views
        addNote = (FloatingActionButton) findViewById(R.id.addNote);
        notesRecyclerView = (RecyclerView) findViewById(R.id.toDoListRecyclerView);

        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesRecyclerView.setHasFixedSize(true);
        notesAdapter = new NotesAdapter(this);
        notesRecyclerView.setAdapter(notesAdapter);

        noteViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);

        noteViewModel.init();
       /* swipe left and right helper*/

        //observer that observes whether there have been changes on the list or not
        noteViewModel.getAllNotes().observe(this, new Observer<List<ToDoEntity>>() {
            @Override
            public void onChanged(List<ToDoEntity> notes) {
                notesAdapter.setNotes(notes);
            }

        });

//        new SwipeHelper(this, notesRecyclerView) {
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                super.onSwiped(viewHolder, direction);
//                Toast.makeText(getApplicationContext(),notesAdapter.getNoteAt(viewHolder.getAdapterPosition()).getNoteText(),Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void instantiateUnderlayButton(final RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
//                underlayButtons.add(new SwipeHelper.UnderlayButton(
//                        "Delete", noteViewModel.delete(notesAdapter.getNoteAt(viewHolder.getAdapterPosition()));
////                                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
//                        0,
//                        Color.parseColor("#000000"),
//                        new SwipeHelper.UnderlayButtonClickListener() {
//                            @Override
//                            public void onClick(int pos) {
//
////
//                            }
//                        }
//                ));
//            }
//        };

//        new SwipeHelperRight(this, notesRecyclerView,"left") {
//            @Override
//            public void instantiateUnderlayButton(final RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
//                underlayButtons.add(new SwipeHelperRight.UnderlayButton(
//                        "Update",
//                        0,
//                        Color.parseColor("#000000"),
//                        new SwipeHelperRight.UnderlayButtonClickListener() {
//                            @Override
//                            public void onClick(int pos) { Toast.makeText(getApplicationContext(),"update",Toast.LENGTH_LONG).show();
////                                noteViewModel.delete(notesAdapter.getNoteAt(viewHolder.getAdapterPosition()));
//                                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                ));
//            }
//        };
//
//
//        new SwipeHelperRight(this, notesRecyclerView,"right") {
//            @Override
//            public void instantiateUnderlayButton(final RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
//                underlayButtons.add(new SwipeHelperRight.UnderlayButton(
//                        "Update",
//                        0,
//                        Color.parseColor("#000000"),
//                        new SwipeHelperRight.UnderlayButtonClickListener() {
//                            @Override
//                            public void onClick(int pos) {
//                                FragmentManager fm = getSupportFragmentManager();
//                                AddNoteFragment editNameDialogFragment = AddNoteFragment.newInstance("Add note");
//                                Bundle bundle = new Bundle();
//                                bundle.putString("isAddOrModify", "modify");
//                                editNameDialogFragment.show(fm, "fragment_edit_name");
//                                bundle.putString("note", notesAdapter.getNoteAt(viewHolder.getAdapterPosition()).getNoteDescription());
//                                bundle.putString("description", notesAdapter.getNoteAt(viewHolder.getAdapterPosition()).getNoteDescription());
//                                bundle.putString("id",String.valueOf(notesAdapter.getNoteAt(viewHolder.getAdapterPosition()).getId()));
//                                editNameDialogFragment.setArguments(bundle);
//                            }
//                        }
//                ));
//            }
//        };

        new SwipeHelperLeftRight(getApplicationContext(), notesRecyclerView) {
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, final int direction) {
                super.onSwiped(viewHolder,direction);
            }

            @Override
            public void instantiateUnderlayButton(final RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                //add an approve button to the right of the item
                underlayButtons.add(new SwipeHelperLeftRight.UnderlayButton(
                        "Edit",
                        R.drawable.ic_edit_black_24dp,
                        getResources().getColor(R.color.colorPrimary),
                        new SwipeHelperLeftRight.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                FragmentManager fm = getSupportFragmentManager();
                                AddNoteFragment editNameDialogFragment = AddNoteFragment.newInstance("Add note");
                                Bundle bundle = new Bundle();
                                bundle.putString("isAddOrModify", "modify");
                                editNameDialogFragment.show(fm, "fragment_edit_name");
                                bundle.putString("note", notesAdapter.getNoteAt(viewHolder.getAdapterPosition()).getNoteDescription());
                                bundle.putString("description", notesAdapter.getNoteAt(viewHolder.getAdapterPosition()).getNoteDescription());
                                bundle.putString("id",String.valueOf(notesAdapter.getNoteAt(viewHolder.getAdapterPosition()).getId()));
                                editNameDialogFragment.setArguments(bundle);
                            }
                        },
                        getApplicationContext(),
                        "right"
                ));
                //add an approve button to the right of the item
                underlayButtons.add(new SwipeHelperLeftRight.UnderlayButton(
                        "Delete",
                        R.drawable.ic_delete_black_24dp,
                        getResources().getColor(R.color.colorPrimary),
                        new SwipeHelperLeftRight.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                noteViewModel.delete(notesAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
//
                            }
                        },
                        getApplicationContext(),
                        "left"
                ));
            }

        };

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddNoteFragment();
            }
        });
    }



    /*method that will add the note fragment to the activity*/
    private void startAddNoteFragment() {

        //starting the add note fragment
        FragmentManager fm = getSupportFragmentManager();
        AddNoteFragment editNameDialogFragment = AddNoteFragment.newInstance("Add note");
        Bundle bundle=new Bundle();
        bundle.putString("isAddOrModify","add");
        editNameDialogFragment.setArguments(bundle);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }


    @Override
    public void onFinishAddNotes(ToDoEntity toDoEntity, String isAddOrModify) {

        //
        ToDoViewModel toDoViewModel = new ToDoViewModel(getApplication());
        String status="";
        if(isAddOrModify.equals("add")) {
            toDoViewModel.insert(toDoEntity);
            status = "Successfully saved";
        }

        else {
            toDoViewModel.update(toDoEntity);
            status = "Successfully updated";
        }

        Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
    }

}
