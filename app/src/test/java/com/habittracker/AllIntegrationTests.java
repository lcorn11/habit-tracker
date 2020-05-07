package com.habittracker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.habittracker.Managers.IntegrationTests.HabitManagerIT;
import com.habittracker.Managers.IntegrationTests.NotesManagerIT;
import com.habittracker.Managers.IntegrationTests.UserManagerIT;

//run only integration tests
@RunWith(Suite.class)
@Suite.SuiteClasses({
        HabitManagerIT.class,
        NotesManagerIT.class,
        UserManagerIT.class
})
public class AllIntegrationTests {
}
