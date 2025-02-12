package com.habittracker.Main;

import android.util.Log;

public class ConnectDB
{
    private static String dbName="SC";

    public static void setDBPathName(final String name) {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            Log.e("Setting up DB driver", e.toString());
            e.printStackTrace();
        }
        dbName = name;
    }

    public static String getDBPathName() {
        return dbName;
    }
}
