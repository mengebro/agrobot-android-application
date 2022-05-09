package com.example.mrga.agrobotapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;

import com.example.mrga.agrobotapp.LoginActivity;

import java.util.HashMap;
public class SessionManager {

    SharedPreferences pref;

    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    Context _context;
    SharedPreferences prefs;

    SharedPreferences.Editor editor;

    Context ctx;


    public SessionManager(Context ctx) {

        this.ctx = ctx;

        prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);

        editor = prefs.edit();

    }


    public void setLoggedin(boolean logggedin) {

        editor.putBoolean("loggedInmode", logggedin);

        editor.commit();

    }


    public boolean loggedin() {

        return prefs.getBoolean("loggedInmode", false);

    }

    public void logout() {

        editor.clear();
        editor.commit();

    }

    public void createLoginSession(String name, String email) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }

}