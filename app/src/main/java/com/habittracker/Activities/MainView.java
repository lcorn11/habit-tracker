package com.habittracker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import com.habittracker.DAO.Habit;
import com.habittracker.DAO.User;
import com.habittracker.Managers.CalendarDateValidator;
import com.habittracker.Managers.HabitListManager;
import com.habittracker.Managers.DateUtil;
import com.habittracker.R;

public class MainView extends AppCompatActivity {

    private static final int ADD_ACTIVITY_ID = 0;
    private TextView txtSelectedDate;
    private CalendarView calendarView;
    private FloatingActionButton btnAddHabit;
    private String selectedDate;

    private User user;
    private HabitListManager habitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view );

        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("user");

        setTitle(String.format("Logged in as: %s", user.getUsername()));
        habitList = new HabitListManager(user);

        txtSelectedDate = findViewById(R.id.txtSelectedDate);
        selectedDate = getCurrentDate(); //get current date formatted
        txtSelectedDate.setText("Today's Date: " + selectedDate); //set date field to show current date

        configList(); //get today habits and attach listener
        configAddButton(); //attach listener to add button
        configCalendar(); //attach listener to calendarView
    }

    @Override
    protected void onResume(){
        super.onResume();
        setContentView(R.layout.main_view );

        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("user");

        setTitle(String.format("Logged in as: %s", user.getUsername()));
        habitList = new HabitListManager(user);

        txtSelectedDate = findViewById(R.id.txtSelectedDate);
        selectedDate = getCurrentDate(); //get current date formatted
        txtSelectedDate.setText("Today's Date: " + selectedDate); //set date field to show current date

        configList(); //get today habits and attach listener
        configAddButton(); //attach listener to add button
        configCalendar(); //attach listener to calendarView
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout){
 //delete saved info
            SharedPreferences sharedPreferences = getSharedPreferences("account", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();

            Intent intent = new Intent( MainView.this, LoginActivity.class);
            startActivity(intent);
            finish(); //remove from back stack
            return true;
        }

        if(item.getItemId() == R.id.manageHabit){
            Intent intent = new Intent( MainView.this, HabitsListView.class);
            intent.putExtra("habitList", habitList);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//when another activity finishes and sends result and data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 //if creating new habit
        if(requestCode == ADD_ACTIVITY_ID && resultCode == Activity.RESULT_OK){
            reloadList(selectedDate); //redisplay the list
        }
    }

//launch addHabit activity, and pass the user object to it
    private void configAddButton(){
        btnAddHabit = findViewById(R.id.btnAddHabit);
        btnAddHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent( MainView.this, AddHabitActivity.class);
                nextActivity.putExtra("user",user);
                startActivityForResult(nextActivity, ADD_ACTIVITY_ID);
            }
        });
    }

    private void configCalendar() {
        calendarView = findViewById(R.id.calendarView);

 //fire's when selected date on the calendarView changes
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
  //Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
                selectedDate = (month + 1) + "/" + dayOfMonth + "/" + year;

                txtSelectedDate.setText("Today's Date: " + selectedDate);
                reloadList(selectedDate); //update the list with the correct habits
            }
        });
    }

 // manually set the current date
    private String getCurrentDate() {
        return DateUtil.formatDate(new Date());
    }

    private void notesAlertBox(final String habitName){
        final Habit currHabit = habitList.getHabit(habitName);

        final AlertDialog.Builder notesDialog = new AlertDialog.Builder( MainView.this);
        notesDialog.setMessage("Would you like to add a note?");
        notesDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                habitList.completeHabit(habitName);
                Toast toast = Toast.makeText(getApplicationContext(), "Completed " + habitName, Toast.LENGTH_SHORT);
                toast.show();

                reloadList(selectedDate);
            }
        });
        notesDialog.setNegativeButton("Add note", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                habitList.completeHabit(habitName);
                Intent nextActivity = new Intent( MainView.this, AddNoteActivity.class);
                nextActivity.putExtra("habit",currHabit);
                startActivity(nextActivity);
            }
        });
        notesDialog.create().show();
    }


    private void configList(){
        ListView list = findViewById(R.id.listView);
        reloadList(selectedDate); //load list for the first time

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                Date selectDate = null;
                Date currentDate = null;

                try {
                    selectDate = DateUtil.parseString(selectedDate);
                    currentDate = CalendarDateValidator.getCurrentDate();
                }catch(ParseException e){
                    e.printStackTrace();
                    Errors.fatalError( MainView.this, "Unable to load calendar date");
                    return;
                }

                if(selectDate.equals(currentDate)){
                    notesAlertBox(selected);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Can't complete habits in future!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

  //load the uncompleted habits for given day
    public void reloadList(String date){
        ListView list = findViewById(R.id.listView);
        ArrayList<Habit> habits;
        try {
            habits = habitList.getUncompletedHabits(date);
        }catch(ParseException e){
            habits = new ArrayList<>();
            Log.e("Uncompleted habit", e.getMessage());
            Errors.warning( MainView.this, "Unable to load habits");
            e.printStackTrace();
        }

 //get only habit names
        ArrayList<String> habitNames = habitList.getHabitNames(habits);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, habitNames);
        list.setAdapter(adapter);
    }
}
