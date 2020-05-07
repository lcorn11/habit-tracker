package com.habittracker.Database;

public interface UserInterface {
    boolean addUser(String username, String password);
    boolean getUser(String username, String password);
    void changePassword(String username, String password);
}
