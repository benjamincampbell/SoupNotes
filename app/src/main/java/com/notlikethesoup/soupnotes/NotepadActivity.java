package com.notlikethesoup.soupnotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

        // Get the ListView that contains the notes
        lvNotes = (ListView) findViewById(R.id.lvNotes);

        // Call the method to read the notes from file into the notes ArrayList
        readNotes();

        // Set up an ArrayAdapter that we fill with the notes ArrayList
        notesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, notes);

        // Set the notesAdapter to be the adapter for the lvNotes view
        lvNotes.setAdapter(notesAdapter);

        // Self explanatory
        setupListViewListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.addnote, menu);

        return true;
    }

    // Sets what happens when someone long-clicks an item in the lvNotes ListView
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

    // Method called when someone clicks the AddNote button (as set in @menu/addnote.xml)
    public void onAddNote(MenuItem mi){
        // create an intent, passing this button and the activity to be started in the intent.
        // We set 1
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null){
            String newNote = data.getStringExtra("com.notlikethesoup.soupnotes.MESSAGE");
            notes.add(newNote);
            notesAdapter.notifyDataSetChanged();
            writeNotes();

        }
    }
    private void readNotes() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
             notes = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
             notes = new ArrayList<String>();
        }
    }

    private void writeNotes() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, notes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
