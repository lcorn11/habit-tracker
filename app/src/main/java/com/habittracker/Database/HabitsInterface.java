package com.habittracker.Database;

import java.util.ArrayList;

import com.habittracker.DAO.Habit;
import com.habittracker.DAO.User;

public interface HabitsInterface {
    ArrayList<Habit> getUserHabits(User user);

    void deleteHabit(Habit habit);

    boolean addHabit(Habit habit);

    boolean edit(Habit habit, Habit newHabit);

    boolean update(Habit habit);
}
