package com.habittracker.Database;

import java.util.ArrayList;

import com.habittracker.DAO.Habit;
import com.habittracker.DAO.Note;

public interface NoteInterface {
    ArrayList<Note> getHabitNotes(Habit habit);

    void addNote(Note note);

    void deleteNote(Note note);

    void editNote(Note note, Note newNote);
}
