package com.habittracker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.EditText;
import android.widget.RadioButton;

import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

import com.habittracker.DAO.Habit;

import com.habittracker.DAO.Note;
import com.habittracker.Managers.NotesManager;
import com.habittracker.Managers.DateUtil;
import com.habittracker.R;


public class AddNoteActivity extends AppCompatActivity {

    private Habit userHabit;
    private String noteDate;
    private Note currentNote;
    private RadioButton No, Maybe, Yes;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_add_note );

        Intent intent = getIntent();
        currentNote = (Note)intent.getSerializableExtra("note");
        userHabit = (Habit) intent.getSerializableExtra("habit");
        noteDate = DateUtil.formatDate(new Date());

//Question: Did this habit make you healthier?
        No = (RadioButton)findViewById(R.id.radio_bad);
        Maybe = (RadioButton)findViewById(R.id.radio_average);
        Yes = (RadioButton)findViewById(R.id.radio_good);
        No.setChecked(false);
        Maybe.setChecked(false);
        Yes.setChecked(false);

        if(currentNote != null){
            int userFeeling = currentNote.getFeeling();
            if(userFeeling == 0){
                No.toggle();
            }else if(userFeeling == 1){
                Maybe.toggle();
            }else if(userFeeling == 2){
                Yes.toggle();
            }
            EditText et = (EditText)findViewById(R.id.etWriteNotes);
            et.setText(currentNote.getNoteText());
        }
        configSaveButton();
    }

  //listener for btnNotes //btnSaveNote -- creates new note
    private void configSaveButton(){
        FloatingActionButton btnSave = findViewById(R.id.btnUpdateNote);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText)findViewById(R.id.etWriteNotes);
                String note = et.getText().toString();
                int feeling = getFeelings();
                if(feeling >-1) {
                    if(currentNote != null && NotesManager.updateNote(currentNote,note,feeling,currentNote.getNoteDate())){
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }else if(currentNote == null && NotesManager.saveNewNote(note, feeling, noteDate, userHabit)){
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }else {
                        Toast.makeText( AddNoteActivity.this,"Note already exists",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText( AddNoteActivity.this,"Please answer the question",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int getFeelings(){
        int feelings = -1;

        if(No.isChecked())
            feelings = 0;
        else if(Maybe.isChecked())
            feelings = 1;
        else if(Yes.isChecked())
            feelings = 2;
        return feelings;
    }
}

