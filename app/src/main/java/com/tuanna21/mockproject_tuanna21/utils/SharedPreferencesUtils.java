package com.tuanna21.mockproject_tuanna21.utils;

import static com.tuanna21.mockproject_tuanna21.utils.Constants.SharedPref.HAS_DATABASE;
import static com.tuanna21.mockproject_tuanna21.utils.Constants.SharedPref.SHARED_FILE_NAME;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
    private static SharedPreferencesUtils sInstance;
    private final SharedPreferences mPref;

    private SharedPreferencesUtils(Context context) {
        mPref = context.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferencesUtils getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharedPreferencesUtils(context);
        }
        return sInstance;
    }

    public void saveString(String key, String value) {
        mPref.edit().putString(key, value).apply();
    }

    public void saveBoolean(String key, boolean value) {
        mPref.edit().putBoolean(key, value).apply();
    }

    public boolean hasDatabaseData() {
        return mPref.getBoolean(HAS_DATABASE, false);
    }

}
