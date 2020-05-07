package com.habittracker.Database.Interface;

import java.util.ArrayList;

import com.habittracker.DAO.Habit;
import com.habittracker.DAO.User;
import com.habittracker.Database.HabitsInterface;

public class HabitsStub implements HabitsInterface {

    private ArrayList<Habit> habits;
    public HabitsStub(){
        habits = new ArrayList<>();
        habits.add(new Habit("workout",5,3, new User("test"),"Morning", 1));
        habits.add(new Habit("piano",7,1, new User("test"),"Afternoon",2));
        habits.add(new Habit("read",7,0, new User("test"),"Morning",1));
        habits.add(new Habit("run",2,0, new User("test"),"Evening",3));
    }

    @Override
    public ArrayList<Habit> getUserHabits(User user) {
        ArrayList<Habit> userHabits = new ArrayList<>();
        for(Habit habit : habits){
            if(habit.getUser().equals(user)){
                userHabits.add(habit);
            }
        }
        return userHabits;
    }

    @Override
    public void deleteHabit(Habit habit) {
        habits.remove(habit);
    }

    @Override
    public boolean addHabit(Habit habit) {
 //only add to list, if the habit doesn't already exist
//two habits are the same if they have the same user, and name
        boolean returnValue = false;
        if(!habits.contains(habit)){
            habits.add(habit);
            returnValue = true;
        }
        return returnValue;
    }

    @Override
    public boolean edit(Habit habit, Habit newHabit) {
        deleteHabit(habit);
        return addHabit(newHabit);
    }

//update the same habit instance in the db
    @Override
    public boolean update(Habit habit) {
        deleteHabit(habit);
        return addHabit(habit);
    }

//test up temp database just for running tests
    public void setTestList(ArrayList<Habit> testList){
        habits = testList;
    }
}
