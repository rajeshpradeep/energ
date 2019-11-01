package com.miniproject.energ.utils;

import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Created by Rajesh Pradeep G on 25-10-2019
 */
public class SharedPreferencesUtil {
    private SharedPreferences mSharedPreferences;

    /**
     * initialize the shared preference
     * */
    @Inject
    public SharedPreferencesUtil(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    public SharedPreferencesUtil() {

    }

    public void clearDateDetails(String key) {
        mSharedPreferences.edit().remove(key).apply();
    }

    /*  Delete all data in sharedPreference*/
    public void deleteAllSession() {
        mSharedPreferences.edit().clear().apply();
    }

    /* Save user Email*/
    public void saveUserEmail(String key, String data) {
        mSharedPreferences.edit().putString(key, data).apply();
    }

    public String getUserEmail(String key) {
        return mSharedPreferences.getString(key, null);
    }

    /* Save user Name from API*/
    public void saveUserName(String key, String data) {
        mSharedPreferences.edit().putString(key, data).apply();
    }

    public String getUserName(String key) {
        return mSharedPreferences.getString(key, null);
    }

    /* Save user mobile*/
    public void saveUserMobile(String key, String data) {
        mSharedPreferences.edit().putString(key, data).apply();
    }

    public String getUsermobile(String key) {
        return mSharedPreferences.getString(key, null);
    }
}
