package com.habittracker.Managers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
//parse a string into a Date object
    public static Date parseString(String date)throws ParseException {
        return new SimpleDateFormat("MM/dd/yyyy").parse(date);
    }

//format a Date object into a String
    public static String formatDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        return formatter.format(date);
    }
}
