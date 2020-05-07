package com.habittracker.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.habittracker.Main.Updates;
import com.habittracker.DAO.User;
import com.habittracker.Managers.HabitManager;
import com.habittracker.Managers.NotesManager;
import com.habittracker.Managers.UserManager;
import com.habittracker.Database.DBUtil;
import com.habittracker.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login );
        DBUtil.copyDatabaseToDevice(getApplicationContext());
        loadManagers(); //setup all the mangers with instances to the db

        SharedPreferences sharedPreferences = getSharedPreferences("account", 0);
        String username = sharedPreferences.getString("username", ""); //get username if its stored, otherwise default to empty string
        String password = sharedPreferences.getString("password", "");

        if(UserManager.login(username, password) == UserManager.SUCCESS){
            launchHomePage(new User(username));
        }

        configLoginButton();
        configRegisterButton();
    }

    private void loadManagers(){
        new UserManager( Updates.getUserInterface());
        new HabitManager( Updates.getHabitsInterface());
        new NotesManager( Updates.getNoteInterface());
    }

    private void launchRegisterView(){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    private void launchHomePage(User user){
        Intent intent = new Intent(LoginActivity.this, MainView.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    private void configLoginButton(){
        final Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText loginUserName = findViewById(R.id.userNameLogin);
                String username = loginUserName.getText().toString();
                if(TextUtils.isEmpty(username)) {
                    loginUserName.setError("Please enter your username");
                    return;
                }

                EditText loginPassword = findViewById(R.id.passwordLogin);
                String password = loginPassword.getText().toString();
                if(TextUtils.isEmpty(password)) {
                    loginPassword.setError("Please enter your password");
                    return;
                }

                if(UserManager.login(username,password) == UserManager.SUCCESS){
                    //save username and pass
                    SharedPreferences sharedPreferences = getSharedPreferences("account", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.commit();

                    launchHomePage(new User(username));
                }else {
                    login.setError("");
                    loginUserName.setError("Incorrect username/password");
                    loginPassword.setError("Incorrect username/password");
                }

            }
        });
    }

    private void configRegisterButton(){
        final Button register = findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                launchRegisterView();
            }
        });
    }
}
