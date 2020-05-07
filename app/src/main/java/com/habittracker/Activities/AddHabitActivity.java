package com.habittracker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.habittracker.DAO.Habit;
import com.habittracker.DAO.User;
import com.habittracker.Managers.HabitListManager;
import com.habittracker.Managers.HabitManager;
import com.habittracker.R;

public class AddHabitActivity extends AppCompatActivity {

    private User user;
    private AlertDialog.Builder builder;
    private Habit editHabit = null;
    private Context context;
    private Button homeButton;
    private HabitListManager habitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        builder = new AlertDialog.Builder(this);

        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        editHabit = (Habit) intent.getSerializableExtra("habit");

        setSpinnerText();
        setSpinnerTime();
        configAddButton();

        if(editHabit != null){
            fillInfo(editHabit);
        }
    }

    private void fillInfo(Habit habit){
        final EditText txtHabitName = findViewById(R.id.txtHabitName);
        final Spinner dropdown = findViewById(R.id.spinner);
        final Spinner dropdownTime = findViewById(R.id.spinnerTime);

        txtHabitName.setText(habit.getHabitName());
        dropdown.setSelection(habit.getWeeklyAmount() - 1);
        dropdownTime.setSelection(habit.getSortByDay() - 1);
    }


    private void setSpinnerText(){
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.HabitWeeklyAmount, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

//second dropdown; removed for now
    private void setSpinnerTime(){
        Spinner timeSpinner = findViewById(R.id.spinnerTime);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.HabitTimes,R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        timeSpinner.setAdapter(adapter);
    }

 //addHabit button
    private void configAddButton(){
        Button btnAddHabit = findViewById(R.id.btnAddHabit);
        final EditText txtHabitName = findViewById(R.id.txtHabitName);
        final Spinner dropdown = findViewById(R.id.spinner);
        final Spinner dropdownTime = findViewById(R.id.spinnerTime);

        btnAddHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String habitName = txtHabitName.getText().toString();
                String timesPerWeek = dropdown.getSelectedItem().toString();
//removed for now
                String schedule = dropdownTime.getSelectedItem().toString();
                int scheduleAssoc;
                if(schedule.equals("Morning"))
                    scheduleAssoc=1;
                else if(schedule.equals("Afternoon"))
                    scheduleAssoc=2;
                else if(schedule.equals("Evening"))
                    scheduleAssoc=3;
                else
                    scheduleAssoc=4;

                Intent intent = new Intent();

//deal with editing a habit
                if(editHabit != null && HabitManager.editHabit(editHabit,habitName,timesPerWeek,user,schedule,scheduleAssoc)){
                    int perWeek = Integer.parseInt(timesPerWeek.substring(0,1));
                    Habit newHabit = new Habit(habitName, perWeek, editHabit.getCompletedWeeklyAmount(), user, schedule, scheduleAssoc);
                    intent.putExtra("habit", newHabit);
                    setResult(RESULT_OK,intent.putExtra(habitName,timesPerWeek));
                    finish();//close activity, returns back to the home screen

//deal with adding a habit
                }else if(HabitManager.saveNewHabit(habitName,timesPerWeek,user,schedule,scheduleAssoc)){ //if habit was saved, close the page
                    setResult(RESULT_OK,intent);
                    finish(); //close activity, returns back to the home screen
                }else{
                    builder.setMessage("Unable to save habit! Make sure habit is unique.").setTitle("Error!");
                    AlertDialog alert = builder.create();
                    alert.setTitle("Error!");
                    alert.show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,HabitsListView.class);
        startActivity(intent);
    }

  //Menu item to go to Habitlistview
   @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if(item.getItemId() == R.id.home){
           Intent nextActivity = new Intent( AddHabitActivity.this, HabitsListView.class);
           nextActivity.putExtra("habitList", habitList);
           nextActivity.putExtra("user",user);
           startActivity(nextActivity);
           return true;
       }
         return super.onOptionsItemSelected( item );
    }


 //Button to go to Habitlistview
   public boolean onOptionsItemSelected(@NonNull Button homeButton) {
        homeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getBaseContext(), HabitsListView.class));
                    }
                }
        );
        return onOptionsItemSelected( homeButton );
    }

}