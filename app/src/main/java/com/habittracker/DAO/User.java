package com.habittracker.DAO;

import java.io.Serializable;

public class User implements Serializable {
    private String username;

    public User(String userName){
        username = userName.trim();
    }

    public String getUsername() {
        return username;
    }

    /*
 two users are equal if they have the same username
Input: other user
     */
    public boolean equals(Object o){
        boolean returnValue = false;

        if(o instanceof User){
            User otherUser = (User) o;
            if(otherUser.username.equalsIgnoreCase(username)){
                returnValue = true;
            }
        }
        return returnValue;
    }
}
