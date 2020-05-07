package com.habittracker.Managers;

import java.util.ArrayList;

import com.habittracker.DAO.Habit;
import com.habittracker.DAO.Note;
import com.habittracker.Database.NoteInterface;
import com.habittracker.Database.Interface.NotesStub;

public class NotesManager {

    private static NoteInterface noteInterface;

    public NotesManager(NoteInterface db){
        noteInterface = db;
    }

    /* return all notes created for a habit
 Input: habit to get all notes for */
    public static ArrayList<Note> getNotes(Habit habit){
        return noteInterface.getHabitNotes(habit);
    }

    /*return the text field of all the notes that are given as an input
 Input: notes to read text from  */
    public static ArrayList<String> getNoteText(ArrayList<Note> notes){
        ArrayList<String> returnList = new ArrayList<>();
        for(Note note : notes){
            returnList.add(note.getNoteText());
        }

        return returnList;
    }

    /* return a note where the habit and noteText equal input
 Input: habit which the note but be created for, and the text of the note
     */
    public static Note getNoteByContents(Habit habit, String text){
        Note returnValue = null;
        //get all notes for the habit
        ArrayList<Note> notes = getNotes(habit);

        //find the one where text matches
        for(Note note : notes){
            if(note.getNoteText().equalsIgnoreCase(text)){
                returnValue = note;
                break;
            }
        }
        return returnValue;
    }

    /* return if the note was added
 Input: date for the note
     */
    public static boolean saveNewNote(String text, int feel, String date, Habit habit){
        boolean returnValue = false;
        //only add note if text isn't empty and note text is unique for this habit
        if(text.length() > 0 && getNoteByContents(habit, text) == null){
            Note note = new Note(text.trim(), feel, date, habit);
            noteInterface.addNote(note);
            returnValue = true;
        }

        return returnValue;
    }

    /*  return if the note was updated
 Input: note and date to replace the old note with
     */
    public static boolean updateNote(Note oldNote, String text, int feel, String date){
        boolean returnValue = false;
        noteInterface.deleteNote(oldNote); //ignore the old note from uniqueness check
//text cant be empty and the new text has to be unique
        if(text.length() > 0 && getNoteByContents(oldNote.getHabit(), text) == null){
            noteInterface.addNote(oldNote); //readd it for it to be updated
            Note note = new Note(text.trim(), feel, date, oldNote.getHabit());
            noteInterface.editNote(oldNote,note);
            returnValue = true;
        }else{
//can't update note, re add the deleted note to the db
            noteInterface.addNote(oldNote);
        }
        return returnValue;
    }

    public static void deleteNote(Note note){
        noteInterface.deleteNote(note);
    }

    public void setupTest(ArrayList<Note> testNotes){
        NotesStub stub = (NotesStub) noteInterface;
        stub.setTestList(testNotes);
    }
}
