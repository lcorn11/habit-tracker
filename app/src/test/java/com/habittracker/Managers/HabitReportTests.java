package com.habittracker.Managers;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.habittracker.DAO.Habit;
import com.habittracker.DAO.Note;
import com.habittracker.Database.NoteInterface;

public class HabitReportTests {

    private Habit habit;
    private HabitReport habitReport;

    @Before
    public void setUp(){
        //mock habit
        habit = mock(Habit.class);
        habitReport = new HabitReport(habit);
    }

    @Test
    public void testGetCompletedThisWeek(){
        when(habit.getWeeklyAmount()).thenReturn(5);
        when(habit.getCompletedWeeklyAmount()).thenReturn(5);
        assertEquals( habitReport.getCompletedThisWeek(),"5(100%)");
        //verify the called methods
        verify(habit).getWeeklyAmount();
        verify(habit).getCompletedWeeklyAmount();
    }

    @Test
    public void testGetLastCompleteDate(){
        String date = DateUtil.formatDate(new Date());
        when(habit.getLastCompletedDate()).thenReturn(date);
        assertEquals( habitReport.getLastCompleteDate(),date);
        //verify the call
        verify(habit).getLastCompletedDate();
    }

    @Test
    public void testGetFavDay(){
        when(habit.getDaysOfWeek()).thenReturn(new int[]{5,0,0,0,0,0,0});
        assertEquals( habitReport.getFavDay(), "Sunday");
        verify(habit).getDaysOfWeek();
    }

    @Test
    public void testGetHabitCompleted(){
        when(habit.getCompletedWeeklyAmount()).thenReturn(5);
        when(habit.getWeeklyAmount()).thenReturn(5);
        assertTrue( habitReport.isCompletedThisweek());
        //check calls
        verify(habit).getCompletedWeeklyAmount();
        verify(habit).getWeeklyAmount();
    }

    @Test
    public void testGetAvgNoteFeeling(){
        ArrayList<Note> notes = new ArrayList<>();
        Note note1 = mock(Note.class);
        Note note2 = mock(Note.class);
        notes.add(note1);
        notes.add(note2);
        when(note1.getFeeling()).thenReturn(0);
        when(note1.getFeeling()).thenReturn(2);

        NoteInterface noteInterface = mock( NoteInterface.class);
        new NotesManager( noteInterface );
        when( noteInterface.getHabitNotes(habit)).thenReturn(notes);

        assertEquals( habitReport.getAvgNoteFeeling(),"Average");

        verify( noteInterface ).getHabitNotes(habit);
        verify(note1).getFeeling();
        verify(note2).getFeeling();
    }

    @Test
    public void testGetShareString(){
        ArrayList<Note> notes = new ArrayList<>();
        NoteInterface noteInterface = mock( NoteInterface.class);
        new NotesManager( noteInterface );
        when( noteInterface.getHabitNotes(habit)).thenReturn(notes);
        when(habit.getDaysOfWeek()).thenReturn(new int[]{5,0,0,0,0,0,0});

        when(habit.getHabitName()).thenReturn("hName");
        when(habit.getCompletedWeeklyAmount()).thenReturn(5);
        when(habit.getWeeklyAmount()).thenReturn(5);
        String shareString = habitReport.getShareString();

        assertTrue(shareString.contains("hName"));
        assertTrue(shareString.contains("Habit completed for this week :)"));

        verify(habit, times(2)).getCompletedWeeklyAmount();
        verify(habit, times(2)).getWeeklyAmount();
        verify(habit).getHabitName();
        verify( noteInterface ).getHabitNotes(habit);
        verify(habit).getDaysOfWeek();
    }
}
