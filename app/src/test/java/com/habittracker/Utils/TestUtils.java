package com.habittracker.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.habittracker.Main.ConnectDB;
import com.habittracker.DAO.Habit;
import com.habittracker.DAO.Note;
import com.habittracker.DAO.User;
import com.habittracker.Managers.DateUtil;

public class TestUtils {

    private static final File DB_SRC = new File("src/main/assets/db/Create.script");

    public static File copyDB() throws IOException {
        final File target = File.createTempFile("temp-db", ".script");

        FileChannel src = new FileInputStream(DB_SRC).getChannel();
        FileChannel dest = new FileOutputStream(target).getChannel();
        dest.transferFrom(src,0, src.size());

        ConnectDB.setDBPathName(target.getAbsolutePath().replace(".script", ""));
        return target;
    }

    //add days to current date
    public static Date addDaysToDate(int days){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,days); //set calendar to a future date
        return cal.getTime();
    }

    public static ArrayList<Habit> createDB(User user){
        ArrayList<Habit> returnList = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            Habit h = new Habit("h"+i,1,0,user,"Afternoon",2);
            returnList.add(h);
        }
        Habit h = new Habit("h"+4,1,0,user,"Morning",1);
        returnList.add(h);
        return returnList;
    }

    public static ArrayList<Note> createNoteDB(Habit habit1, Habit habit2){
        ArrayList<Note> returnList = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            Note note = new Note("H1note" + i, 1, DateUtil.formatDate(new Date()), habit1);
            returnList.add(note);
        }

        for(int i = 0; i < 2; i++){
            Note note = new Note("H2note" + i, 1, DateUtil.formatDate(new Date()), habit2);
            returnList.add(note);
        }

        return returnList;
    }
}
