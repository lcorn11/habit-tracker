package com.habittracker.Database.Interface;

import java.util.HashMap;
import java.util.Map;

import com.habittracker.Database.UserInterface;

public class UserStub implements UserInterface {
    private Map<String, String> users;

    public UserStub(){
        users = new HashMap<>();
        users.put("test", "test");
        users.put("user", "pass");
    }

    @Override
    public boolean addUser(String username, String password) {
        boolean returnValue = false;
        if(!users.keySet().contains(username)){
            users.put(username, password);
            returnValue = true;
        }
        return returnValue;
    }

    @Override
    public boolean getUser(String username, String password){
        boolean returnValue = false;
        if(users.keySet().contains(username) && users.get(username).equals(password)){
            returnValue = true;
        }
        return returnValue;
    }

    @Override
    public void changePassword(String username, String password) {
        return;
    }
}
