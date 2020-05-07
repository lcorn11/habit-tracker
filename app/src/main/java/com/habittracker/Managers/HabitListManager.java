package com.habittracker.Managers;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

import com.habittracker.DAO.Habit;
import com.habittracker.DAO.User;


public class HabitListManager implements Serializable {

    private User user;

    public HabitListManager(User user){
        this.user = user;
    }


//return a list of all the habits for the user
    public ArrayList<Habit> getHabits(){
        ArrayList<Habit> habits = HabitManager.getHabits(user);

 //if in a new week, reset completed counter
        try{
            completedAmountCheck(habits);
        }catch(ParseException e){
            Log.e("Updating complete amt", e.toString());
            e.printStackTrace();
        }

        Collections.sort(habits); //sort on intitial creation
        return habits;
    }

 /* return list of all the habits that uncompleted for the selected date
 Input: selected date */
    public ArrayList<Habit> getUncompletedHabits(String date)throws ParseException {
        ArrayList<Habit> returnHabits = new ArrayList<>();
        ArrayList<Habit> habits = getHabits();
        for(Habit habit : habits){
            //habit is uncompleted if selected date is valid and isn't in current week, and habit completed amount hasn't reached the desired amount
            if(!HabitDateValidator.isCompleted(habit,date) && CalendarDateValidator.isValidDate(date)){
                returnHabits.add(habit);
            }
        }
        return returnHabits;
    }

 /* return a list of only names of habits
 Input: list of habit */
    public ArrayList<String> getHabitNames(ArrayList<Habit> habits){
        ArrayList<String> returnNames = new ArrayList<>();
        for(Habit habit : habits){
            returnNames.add(habit.getHabitName());
        }
        return returnNames;
    }

 /*  Input: name of the habit to be completed  */
    public void completeHabit(String name){
        ArrayList<Habit> habits = getHabits();
        for(Habit habit: habits){
            //find the habit with the right name
            if(habit.getHabitName().equals(name)){
                //complete the habit and update database
                habit.complete();
                HabitManager.updateHabit(habit);
                break;
            }
        }
    }

/* return a habit
 Input: name of habit */
    public Habit getHabit(String name){
        Habit returnHabit = null;
        ArrayList<Habit> habits = getHabits();
        for(Habit habit: habits){
//find the habit
            if(habit.getHabitName().equals(name))
                returnHabit = habit;
        }
        return returnHabit;
    }

    /*  check if habit was completed in the last week,
if so reset it's checked amount and update in the database */
    private void completedAmountCheck(ArrayList<Habit> habits)throws ParseException{
        for(Habit habit : habits){
            if(HabitDateValidator.updateCompletedAmount(habit)){
               HabitManager.updateHabit(habit);
            }
        }
    }
}
