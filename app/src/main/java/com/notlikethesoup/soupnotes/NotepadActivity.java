package com.notlikethesoup.soupnotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class NotepadActivity extends AppCompatActivity {

    private ArrayList<String> notes;
    private ArrayAdapter<String> notesAdapter;
    private ListView lvNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        //   ^ actually displays the /res/layout/activity_notepad.xml

        lvNotes = (ListView) findViewById(R.id.lvNotes);
        notes = new ArrayList<>();
        notesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, notes);
        lvNotes.setAdapter(notesAdapter);
        notes.add("First");
        notes.add("Second");
    }

    public void onAddNote(View view){
        Intent intent = new Intent(this, AddNoteActivity.class);

        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null){
            String newNote = data.getStringExtra("com.notlikethesoup.soupnotes.MESSAGE");
            notes.add(newNote);
            notesAdapter.notifyDataSetChanged();
        }
    }
}
