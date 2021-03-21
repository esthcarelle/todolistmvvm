package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.ToDoEntity;
import com.example.myapplication.fragments.AddNoteFragment;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends PagedListAdapter<ToDoEntity, NotesAdapter.ViewHolder> {
    List<ToDoEntity> notes = new ArrayList<>();
    Context context;

    public NotesAdapter(Context context)
    {
        super(ToDoEntity.DIFF_CALLBACK);
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todo_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if(notes.get(i)!=null) {
            final String note = notes.get(i).getNoteText();
            final String description = notes.get(i).getNoteDescription();
            final long id = notes.get(i).getId();
            if(note!=null)
            viewHolder.title.setText(note.replaceAll("\\s+", " "));

            if(description!=null)
            viewHolder.description.setText(description.replaceAll("\\s+", " "));
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    //add the fragment to the activity
//                    FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
//                    AddNoteFragment editNameDialogFragment = AddNoteFragment.newInstance("Add note");
//                    Bundle bundle = new Bundle();
//                    bundle.putString("isAddOrModify", "modify");
//                    editNameDialogFragment.show(fm, "fragment_edit_name");
//                    bundle.putString("note", note);
//                    bundle.putString("description", description);
//                    bundle.putString("id",String.valueOf(id));
//                    editNameDialogFragment.setArguments(bundle);
//                }
//            });
        }
    }

    public ToDoEntity getNoteAt(int position) {
        return notes.get(position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<ToDoEntity> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.toDo);
            description = itemView.findViewById(R.id.toDoDescription);
        }

    }

    }
