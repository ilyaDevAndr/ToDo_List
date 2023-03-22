package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycleViewNotes;
    private FloatingActionButton buttonAddNote;
    private NotesAdapter notesAdapter;

    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initViews();
        notesAdapter = new NotesAdapter();

        recycleViewNotes.setAdapter(notesAdapter);

        viewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesAdapter.setNotes(notes);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper
                (new ItemTouchHelper.SimpleCallback
                        (0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove
                            (@NonNull RecyclerView recyclerView,
                             @NonNull RecyclerView.ViewHolder viewHolder,
                             @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Note note = notesAdapter.getNotes().get(position);
                        viewModel.romeve(note);
                    }
                });
        itemTouchHelper.attachToRecyclerView(recycleViewNotes);


        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = create_list_add.newIntent(MainActivity.this);
                startActivity(intent);


            }
        });
    }


    private void initViews() {
        recycleViewNotes = findViewById(R.id.recycleViewNotes);
        buttonAddNote = findViewById(R.id.buttonAddNote);
    }


}


