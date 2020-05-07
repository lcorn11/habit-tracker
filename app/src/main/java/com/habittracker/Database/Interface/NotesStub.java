package com.habittracker.Database.Interface;

import java.util.ArrayList;
import java.util.Date;

import com.habittracker.DAO.Habit;
import com.habittracker.DAO.Note;
import com.habittracker.DAO.User;
import com.habittracker.Managers.DateUtil;
import com.habittracker.Database.NoteInterface;

public class NotesStub implements NoteInterface {

    ArrayList<Note> notes;

    public NotesStub(){
        notes = new ArrayList<>();
        Habit habit = new Habit("workout",5,3, new User("test"),"Morning", 1);
        notes.add(new Note("yep", 5, DateUtil.formatDate(new Date()), habit));
        notes.add(new Note("note123", 1, DateUtil.formatDate(new Date()), habit));
        habit = new Habit("piano",7,1, new User("test"),"Afternoon",2);
        notes.add(new Note("good job", 5, DateUtil.formatDate(new Date()), habit));
        notes.add(new Note("keep it up", 1, DateUtil.formatDate(new Date()), habit));
    }

    @Override
    public ArrayList<Note> getHabitNotes(Habit habit) {
        ArrayList<Note> habitNotes= new ArrayList<>();
        for(Note note : notes){
            if(note.getHabit().equals(habit)){
                habitNotes.add(note);
            }
        }
        return habitNotes;
    }

    @Override
    public void addNote(Note note) {
        notes.add(note);
    }

    @Override
    public void deleteNote(Note note) {
        for(int i = 0; i < notes.size(); i++){
            if(notes.get(i).equals(note)){
                notes.remove(i);
                break;
            }
        }
    }

    @Override
    public void editNote(Note note, Note newNote) {
        deleteNote(note);
        addNote(newNote);
    }

//test up temp database just for running tests
    public void setTestList(ArrayList<Note> testList){
        notes = testList;
    }
}
