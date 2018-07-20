package com.yamschikov.dima.justtodo.di;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class SharedPreferencesManager {

    private SharedPreferences mSharedPreferences;

    // Shared preferences file name
    private static final String PREF_NAME = "androidhive-welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String PREF_USER_NAME = "prefusername";
    private static final String PREF_USER_EMAIL = "prefuseremail";
    private static final String PREF_USER_PIC = "prefuserpic";

    private static final String PREF_CHECKED_CATEGORY = "prefcheckedcategory";
    private static final String PREF_CHECKED_DATE = "prefcheckeddate";

    private static final String PREF_CHECK_SIGNOUT = "prefchecksignout";


    @Inject
    public SharedPreferencesManager(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        mSharedPreferences.edit().putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime).apply();
    }

    public boolean isFirstTimeLaunch() {
        return mSharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    //prefuser

    public void setFirstUser(String prefUserName, String prefUserEmail, String prefUserPic) {
        mSharedPreferences.edit().putString(PREF_USER_NAME, prefUserName).apply();
        mSharedPreferences.edit().putString(PREF_USER_EMAIL, prefUserEmail).apply();
        mSharedPreferences.edit().putString(PREF_USER_PIC, prefUserPic).apply();}

    public String getFirstUserName() {
        return mSharedPreferences.getString(PREF_USER_NAME, "");
    }

    public String getFirstUserEmail() {
        return mSharedPreferences.getString(PREF_USER_EMAIL, "");
    }

    public String getFirstUserPic() {
        return mSharedPreferences.getString(PREF_USER_PIC, "");
    }

    //add task param
    public void setCheckedCategory(String checkedCategory) {
        mSharedPreferences.edit().putString(PREF_CHECKED_CATEGORY, checkedCategory).apply();
    }

    public String getCheckedCategory() {
        return mSharedPreferences.getString(PREF_CHECKED_CATEGORY, "");
    }

    public String getCheckedDate() {
        return mSharedPreferences.getString(PREF_CHECKED_DATE, "");
    }

    public void setCheckedDate(String checkedDate) {
        mSharedPreferences.edit().putString(PREF_CHECKED_DATE, checkedDate).apply();
    }

    public int getCheckSignOut() {
        return mSharedPreferences.getInt(PREF_CHECK_SIGNOUT, 1001);
    }

    public void setCheckSignOut(int checkSignOut) {
        mSharedPreferences.edit().putInt(PREF_CHECK_SIGNOUT, checkSignOut).apply();
    }
}