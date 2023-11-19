package com.e2p.myecf.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class MySettings {

    //Shared Preferences Keys
    // Booleans
    public static final String KEY_FIRST_START = "firstStart";
    public static final String KEY_FILTER_COLLAPSED = "nfilterCollapsed";

    public static final String KEY_CUR_LANGUAGE = "curLanguage";

    // Sharedpref file name
    private static final String PREF_NAME = "E2P_ECF_Settings";
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Constructor
    public MySettings(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**********************************(  Getters & Setters )**************************************/
    // String
    public String getCurLanguage() {
        return pref.getString(KEY_CUR_LANGUAGE, "");
    }

    public void setCurLanguage(String curLanguage) {
        editor.putString(KEY_CUR_LANGUAGE, curLanguage);
        editor.commit();
    }

    // Booleans
    public boolean getFirstStart() {
        return pref.getBoolean(KEY_FIRST_START, true);
    }

    public void setFirstStart(boolean firstStart) {
        editor.putBoolean(KEY_FIRST_START, firstStart);
        editor.commit();
    }

    public boolean getFilterCollapsed() {
        return pref.getBoolean(KEY_FILTER_COLLAPSED, false);
    }

    public void setFilterCollapsed(boolean filterCollapsed) {
        editor.putBoolean(KEY_FILTER_COLLAPSED, filterCollapsed);
        editor.commit();
    }

    /*****************************************(  _______  )****************************************/
    //Clear MySettings details
    public void clearSettingsDetails() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }


}
