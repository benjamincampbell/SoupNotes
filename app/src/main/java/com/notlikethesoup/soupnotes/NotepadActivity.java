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
    private ListView elvNotes;
    private int noteNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        //   ^ actually displays the /res/layout/activity_notepad.xml
        elvNotes = (ListView) findViewById(R.id.elvNotes);
        readNotes();
        notesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, notes);
        elvNotes.setAdapter(notesAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {
        elvNotes.setOnItemLongClickListener(
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
            notesAdapter.notifyDataSetChanged();
            writeNotes();

        }
    }
    private void readNotes() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
             notes= new ArrayList<String>(FileUtils.readLines(todoFile));
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
