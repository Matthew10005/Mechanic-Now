package com.example.mechanicnow;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_NAME = "full_name";
    public static final String KEY_DATE = "date of birth";
    public static final String KEY_EMAIL = "email_address";
    public static final String KEY_PHONE = "phone_number";
    public static final String KEY_PASSWORD = "pass_word";

    public SessionManager(Context _context) {
        context = _context;
        sharedPreferences = context.getSharedPreferences("sharedLoginPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void createLoginSession(String nameFromDB, String dateFromDB, String emailFromDB, String phoneFromDB) {
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_NAME, nameFromDB);
        editor.putString(KEY_DATE, dateFromDB);
        editor.putString(KEY_EMAIL, emailFromDB);
        editor.putString(KEY_PHONE, phoneFromDB);
        //editor.putString(KEY_PASSWORD, passFromDB);

        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_NAME, sharedPreferences.getString(KEY_NAME, null));
        userData.put(KEY_DATE, sharedPreferences.getString(KEY_DATE, null));
        userData.put(KEY_EMAIL, sharedPreferences.getString(KEY_EMAIL, null));
        userData.put(KEY_PHONE, sharedPreferences.getString(KEY_PHONE, null));
        userData.put(KEY_PASSWORD, sharedPreferences.getString(KEY_PASSWORD, null));

        return userData;
    }

    public Boolean checkLogin() {
        if (sharedPreferences.getBoolean(IS_LOGIN, false)) {
            return true;
        } else {
            return false;
        }
    }

    public void logoutUserSession() {
        editor.clear();
        editor.commit();
    }

}
