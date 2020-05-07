package com.habittracker.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.habittracker.DAO.User;
import com.habittracker.Managers.UserManager;
import com.habittracker.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);
        configRegisterButton();
    }

    private void launchHomePage(User user){
        Intent intent = new Intent(RegisterActivity.this, MainView.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    private void configRegisterButton(){
        Button registerDone = findViewById(R.id.registerDone);

        registerDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText userNameEdit = findViewById(R.id.userNameRegister);
                String username = userNameEdit.getText().toString();
                if(TextUtils.isEmpty(username)) {
                    userNameEdit.setError("Please enter your username");
                    return;
                }

                EditText passwordEdit = findViewById(R.id.passwordRegister);
                String password = passwordEdit.getText().toString();
                if(TextUtils.isEmpty(password)) {
                    passwordEdit.setError("Please enter your password");
                    return;
                }

                if(UserManager.register(username,password) == UserManager.SUCCESS){
                    //store info for next time
                    SharedPreferences sharedPreferences = getSharedPreferences("account", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);

                    launchHomePage(new User(username));
                }else if(UserManager.register(username,password) == UserManager.INPUT_FAIL){
                    Errors.warning(RegisterActivity.this, "Invalid input");
                    userNameEdit.setError("Make sure username is 20 or less characters");
                    passwordEdit.setError("Make sure password is 20 or less characters");
                }
                else{
                    Errors.fatalError(RegisterActivity.this, "Unable to register user.");
                }
            }
        });
    }

}
