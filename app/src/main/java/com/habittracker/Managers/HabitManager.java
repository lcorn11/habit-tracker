package com.habittracker.Managers;

import java.util.ArrayList;

import com.habittracker.DAO.Habit;
import com.habittracker.DAO.User;
import com.habittracker.Database.HabitsInterface;
import com.habittracker.Database.Interface.HabitsStub;

//manage habits
//add, remove, edit
public class HabitManager {

    private static HabitsInterface habitsInterface;

    public HabitManager(HabitsInterface db){
        habitsInterface = db;
    }

    /*return true if the habit was saved
 Input: habit info
     */
    public static boolean saveNewHabit(String name, String timesPerWeek, User user, String timeOfDay, int timeAssoc){
        boolean returnValue = false;
 //timesPerWeek, parse the first char into an int
        int perWeek = Integer.parseInt(timesPerWeek.substring(0,1));
//if a habit name exists
        if(name.length() > 0){
            Habit habit = new Habit(name,perWeek,0,user, timeOfDay, timeAssoc);
            returnValue = habitsInterface.addHabit(habit);
        }
        return returnValue;
    }

 //write to habitsinterface
    public static boolean editHabit(Habit oldHabit,String name, String timesPerWeek, User user, String timeOfDay, int timeAssoc){
        boolean returnValue = false;
        int perWeek = Integer.parseInt(timesPerWeek.substring(0,1));
        if(name.length() > 0){
            Habit habit = new Habit(name,perWeek,oldHabit.getCompletedWeeklyAmount(),user, timeOfDay, timeAssoc);
            returnValue = habitsInterface.edit(oldHabit,habit);
        }
        return returnValue;
    }

    public static boolean updateHabit(Habit habit){
        return habitsInterface.update(habit);
    }

    public static void delete(Habit habit){
        habitsInterface.deleteHabit(habit);
    }


 //return all habits created by a user
    public static ArrayList<Habit> getHabits(User user){
        return habitsInterface.getUserHabits(user);
    }

//temp database just for tests
    public void setupTest(ArrayList<Habit> testHabits){
        HabitsStub stub = (HabitsStub) habitsInterface;
        stub.setTestList(testHabits);
    }

}
