package com.habittracker.Managers.IntegrationTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.habittracker.DAO.Habit;
import com.habittracker.DAO.Note;
import com.habittracker.DAO.User;
import com.habittracker.Managers.NotesManager;
import com.habittracker.Managers.DateUtil;
import com.habittracker.Database.DB.NoteDB;
import com.habittracker.Database.NoteInterface;
import com.habittracker.Utils.TestUtils;

public class NotesManagerIT {
    private File tempDB;
    private Habit habit;

    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyDB();
        NoteInterface noteInterface = new NoteDB(tempDB.getAbsolutePath().replace(".script",""));
        new NotesManager( noteInterface );
        habit = new Habit("Workout", 1,0,new User("userB"), "Morning", 1);
    }

    @Test
    public void testGetNotes(){
        //should have 2 notes from userB for the "workout" habit
        assertEquals(2, NotesManager.getNotes(habit).size());
    }

    @Test
    public void testNoteByContents(){
        Note note = NotesManager.getNoteByContents(habit, "note1");
        //name of the note user should equal name of habit user
        assertEquals("userB", note.getHabit().getUser().getUsername());

        //make sure right note object is returned
        assertEquals("note1", note.getNoteText());
    }

    @Test
    public void testSaveNewNote(){
        NotesManager.saveNewNote("note3", 1, DateUtil.formatDate(new Date()), habit);

        //new note was added
        assertEquals(3, NotesManager.getNotes(habit).size());
    }

    @Test
    public void testUpdateNote(){
        Note oldNote = new Note("note1", 2, "'04/03/2020'", habit);
        NotesManager.updateNote(oldNote, "updatenote", 1, "'04/03/2020'");
        oldNote = NotesManager.getNoteByContents(habit, "updatenote");
        //updated note exists
        assertNotEquals(null, oldNote);
    }

    @Test
    public void testDeleteNote(){
        Note note = new Note("note1", 2, "'04/03/2020'", habit);
        NotesManager.deleteNote(note);

        //only one note left for "workout" habit
        assertEquals(1,NotesManager.getNotes(habit).size());
    }

    @After
    public void tearDown() {
        // reset DB
        tempDB.delete();
    }
}
