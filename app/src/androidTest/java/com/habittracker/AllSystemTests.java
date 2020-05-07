package com.habittracker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.habittracker.SystemTests.ManageHabitTests;
import com.habittracker.SystemTests.ManageNotesTest;
import com.habittracker.SystemTests.TestCalendarView;
import com.habittracker.SystemTests.TestHabitReport;
import com.habittracker.SystemTests.UserAccountTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ManageHabitTests.class,
        ManageNotesTest.class,
        TestCalendarView.class,
        TestHabitReport.class,
        UserAccountTests.class
})

public class AllSystemTests {
}
