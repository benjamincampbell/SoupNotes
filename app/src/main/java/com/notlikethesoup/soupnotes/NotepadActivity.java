package com.notlikethesoup.soupnotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
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
        readNotes();
        notes = new ArrayList<>();
        notesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, notes);
        lvNotes.setAdapter(notesAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {
        lvNotes.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapter,
                                               View item, int pos, long id) {
                    //This is where the code to actually remove the item goes
                    notes.remove(pos);
                    notesAdapter.notifyDataSetChanged();
                    writeNotes();
                    return true;
                }
            }
        );
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
            writeNotes();
            notesAdapter.notifyDataSetChanged();
        }
    }

    private void readNotes() {
        File filesDir = getFilesDir();
        File notesFile = new File(filesDir, "notes.txt");
        try {
            notes = new ArrayList<String>(FileUtils.readLines(notesFile));
        } catch (IOException e) {
            notes = new ArrayList<>();
        }
    }

    private void writeNotes() {
        File filesDir = getFilesDir();
        File notesFile = new File(filesDir, "notes.txt");
        try {
            FileUtils.writeLines(notesFile, notes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
