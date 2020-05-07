package com.habittracker.Managers.IntegrationTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

import com.habittracker.DAO.Habit;
import com.habittracker.DAO.User;
import com.habittracker.Managers.HabitManager;
import com.habittracker.Database.DB.HabitsDB;
import com.habittracker.Database.HabitsInterface;
import com.habittracker.Utils.TestUtils;

public class HabitManagerIT {
    private File tempDB;
    private Habit habit;
    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyDB();
        HabitsInterface habitsInterface = new HabitsDB(tempDB.getAbsolutePath().replace(".script",""));
        new HabitManager( habitsInterface );
        habit = new Habit("Run", 1,0, new User("test"),"Morning", 1);
    }

    @Test
    public void testSaveNewHabit(){
        int beforeSize = HabitManager.getHabits(new User("test")).size();
        HabitManager.saveNewHabit("new","5", new User("test"), "Morning", 1);
        int size = HabitManager.getHabits(new User("test")).size();

        //the size of the habits for userA should increase by one after adding
        assertEquals(beforeSize + 1, size);
    }

    @Test
    public void testEditHabit(){
        HabitManager.editHabit(habit, "run2", "1", new User("test"), "Morning", 1);

        //no new habit has been added
        assertEquals(1, HabitManager.getHabits(new User("test")).size());
        //name should of been updated
        assertEquals("run2", HabitManager.getHabits(new User("test")).get(0).getHabitName());
    }

    @Test
    public void testUpdateHabit(){
        HabitManager.updateHabit(habit);

        //no new habit has been added
        assertEquals(1, HabitManager.getHabits(new User("test")).size());
    }

    @Test
    public void testDelete(){
        HabitManager.delete(habit);

        //no habit should be left for userA
        assertEquals(0, HabitManager.getHabits(new User("test")).size());
    }

    @Test
    public void testGetHabits(){
        //returns only the habit for userA
        assertEquals(1, HabitManager.getHabits(new User("test")).size());
    }

    @After
    public void tearDown() {
        // reset DB
        tempDB.delete();
    }

}
