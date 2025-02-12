package com.habittracker.DAO;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.habittracker.Managers.DateUtil;

//represents a habit
public class Habit implements Comparable<Habit>, Serializable {

    private String habitName;
    private int weeklyAmount; //weekly goal amount
    private int completedWeeklyAmount; //completed in current week
    private String lastCompletedDate;
    private User user;
    private String timeOfDay;
    private int sortByDay; //removed
    private int totalCompletedAmt;
    private String createdDate;
    //index 0 = sunday, 6 = saturday
    private int[] daysOfWeek;

    public Habit(String name, int weeklyAmt, int completedWeekAmt, User user, String time,int num) {
        habitName = name;
        weeklyAmount = weeklyAmt;
        completedWeeklyAmount = completedWeekAmt;
        this.user = user;
        timeOfDay = time;
        sortByDay = num;
        createdDate = DateUtil.formatDate(new Date());
        totalCompletedAmt = 0;
        daysOfWeek = new int[]{0,0,0,0,0,0,0};
    }

    public Habit(String name, int weeklyAmt, int completedWeekAmt, User user, String time,int num,String date, String createDate, int completeAmt, int[] array) {
        habitName = name;
        weeklyAmount = weeklyAmt;
        completedWeeklyAmount = completedWeekAmt;
        this.user = user;
        timeOfDay = time;
        sortByDay = num;
        lastCompletedDate = date;
        createdDate = createDate;
        totalCompletedAmt = completeAmt;
        daysOfWeek = array;
    }

    public int[] getDaysOfWeek(){
        return daysOfWeek;
    }

    public int getTotalCompletedAmt(){
        return totalCompletedAmt;
    }

    public String getCreatedDate(){
        return createdDate;
    }

    public String getHabitName() {
        return habitName;
    }

    public int getWeeklyAmount() {
        return weeklyAmount;
    }

    public String getTimeOfDay(){
        return timeOfDay;
    }

    public int getSortByDay(){
        return sortByDay;
    }

    public int getCompletedWeeklyAmount() {
        return completedWeeklyAmount;
    }

    /*
return if this habit is completed for a given date
 Input is the desired date
     */
    public boolean isCompleted(String selectedDate) throws ParseException{
        return completedWeeklyAmount >= weeklyAmount || isSameDay(selectedDate);
    }

    public String getLastCompletedDate() {
        return lastCompletedDate;
    }

    public void clearCompletedAmount(){
        completedWeeklyAmount = 0;
    }

// set lastCompletedDate to current date and increase completedWeeklyAmt by one
    public void complete(){

        lastCompletedDate = DateUtil.formatDate(new Date());
 //increase completed amount
        completedWeeklyAmount++;
        totalCompletedAmt++;
        Calendar calendar = Calendar.getInstance();
 //keep track of what day habit was completed
//array is indexed with 0 being sunday and 6 being saturday
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        daysOfWeek[day-1]++;
    }

    public User getUser() {
        return user;
    }

    /* return true two habits are equals, two habits are equal if they have the same name and user
 Input other habit */
    @Override
    public boolean equals(Object obj) {
        boolean returnValue = false;
        if (obj instanceof Habit) {
            Habit otherHabit = (Habit) obj;
            if (otherHabit.habitName.trim().equalsIgnoreCase(habitName.trim()) && otherHabit.user.equals(user)) {
                returnValue = true;
            }
        }
        return returnValue;
    }

    /* sort two habits based to selected time of day
  Input other habit */
    @Override
    public int compareTo(Habit o) {
        int iCompare = o.sortByDay;
        return this.getSortByDay()-iCompare;
    }

    /* compare current selected date to the lastCompletedDate
Input: date being compared to lastCompletedDate */
    private boolean isSameDay(String date)throws ParseException {
        boolean returnValue = false;
        if(lastCompletedDate != null) {
            Date selectedDate = DateUtil.parseString(date);
            Date lastDoneDate = DateUtil.parseString(lastCompletedDate);
            returnValue = selectedDate.equals(lastDoneDate);
        }
        return returnValue;
    }
}
