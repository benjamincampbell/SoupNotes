package com.notlikethesoup.soupnotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class AddNoteActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.notlikethesoup.soupnotes.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        EditText editText = (EditText) findViewById(R.id.editNote);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        editText.requestFocus();
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.addnote, menu);

        return true;
    }

    public void onConfirmNote(MenuItem mi){
        Intent resultIntent = new Intent();
        EditText editText = (EditText) findViewById(R.id.editNote);
        String noteText = editText.getText().toString();
        resultIntent.putExtra(EXTRA_MESSAGE, noteText);

        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
