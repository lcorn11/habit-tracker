package com.habittracker.Activities;

import android.app.AlertDialog;
import android.content.Context;

public class Errors {
    public static void fatalError(final Context context, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage(message);
        alertDialog.show();
    }

    public static void warning(final Context context, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage(message);
        alertDialog.show();
    }

}
