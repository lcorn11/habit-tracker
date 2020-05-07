package com.habittracker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.habittracker.DAO.HabitTests;
import com.habittracker.DAO.NoteTests;
import com.habittracker.Managers.CalendarDateValidatorTests;
import com.habittracker.Managers.HabitDateValidatorTests;
import com.habittracker.Managers.HabitListManagerTests;
import com.habittracker.Managers.HabitManagerTests;
import com.habittracker.Managers.HabitReportTests;
import com.habittracker.Managers.IntegrationTests.HabitManagerIT;
import com.habittracker.Managers.IntegrationTests.NotesManagerIT;
import com.habittracker.Managers.IntegrationTests.UserManagerIT;
import com.habittracker.Managers.NotesManagerTests;
import com.habittracker.Managers.UserManagerTests;

//run all the tests, include unit and integration
@RunWith(Suite.class)
@Suite.SuiteClasses({
        HabitManagerIT.class,
        NotesManagerIT.class,
        UserManagerIT.class,
        NotesManagerTests.class,
        CalendarDateValidatorTests.class,
        HabitDateValidatorTests.class,
        HabitListManagerTests.class,
        HabitManagerTests.class,
        HabitTests.class,
        NoteTests.class,
        HabitReportTests.class,
        UserManagerTests.class
})
public class AllTests {
}
