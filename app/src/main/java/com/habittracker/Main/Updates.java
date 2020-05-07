package com.habittracker.Main;

import com.habittracker.Database.DB.HabitsDB;
import com.habittracker.Database.DB.NoteDB;
import com.habittracker.Database.DB.UserDB;
import com.habittracker.Database.HabitsInterface;
import com.habittracker.Database.NoteInterface;
import com.habittracker.Database.UserInterface;

public class Updates {
    private static HabitsInterface habitsInterface = null;
    private static NoteInterface noteInterface = null;
    private static UserInterface userInterface = null;

    public static synchronized HabitsInterface getHabitsInterface(){
        if(habitsInterface == null){
           habitsInterface = new HabitsDB( ConnectDB.getDBPathName());
        }
        return habitsInterface;
    }

    public static synchronized NoteInterface getNoteInterface(){
        if(noteInterface == null){
            noteInterface = new NoteDB( ConnectDB.getDBPathName());
        }

        return noteInterface;
    }

    public static synchronized UserInterface getUserInterface(){
        if(userInterface == null){
            userInterface = new UserDB( ConnectDB.getDBPathName());
        }
        return userInterface;
    }
}
