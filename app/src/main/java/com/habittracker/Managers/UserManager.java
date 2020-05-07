package com.habittracker.Managers;

import com.habittracker.Database.UserInterface;

public class UserManager {

    public static final int SUCCESS = 0;
    public static final int DB_FAIL = 1;
    public static final int INPUT_FAIL = 2;
    private static UserInterface userInterface;

    public UserManager(UserInterface db){
        userInterface = db;
    }

    public static int login(String username, String password){
        int returnVal = SUCCESS;
        if(username.length() > 0 && password.length() > 0){
            if(!userInterface.getUser(username,password)){
                returnVal = DB_FAIL;
            }
        }else{
            returnVal = INPUT_FAIL;
        }
        return returnVal;
    }

//register user password, removed
    public static int register(String username, String password){
        int returnVal = SUCCESS;
        if(username.length() <= 20 && password.length() <= 20 && username.length() > 0 && password.length() > 0){
            if(!userInterface.addUser(username, password)){
                returnVal = DB_FAIL;
            }
        }else{
            returnVal = INPUT_FAIL;
        }
        return returnVal;
    }

 //change password, removed
    public static int changePassword(String username, String password){
        int returnVal = SUCCESS;
        if(username.length() <= 20 && password.length() <= 20 && username.length() > 0 && password.length() > 0){
            userInterface.changePassword(username, password);
        }else{
            returnVal = INPUT_FAIL;
        }
        return returnVal;
    }
}
