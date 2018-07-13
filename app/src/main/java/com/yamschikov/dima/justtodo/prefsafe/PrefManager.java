package com.yamschikov.dima.justtodo.prefsafe;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "androidhive-welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String PREF_USER_NAME = "prefusername";
    private static final String PREF_USER_EMAIL = "prefuseremail";
    private static final String PREF_USER_PIC = "prefuserpic";

    private static final String PREF_CHECKED_CATEGORY = "prefcheckedcategory";
    private static final String PREF_CHECKED_DATE = "prefcheckeddate";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    //prefuser

    public void setFirstUser(String prefUserName, String prefUserEmail, String prefUserPic) {
        editor.putString(PREF_USER_NAME, prefUserName);
        editor.putString(PREF_USER_EMAIL, prefUserEmail);
        editor.putString(PREF_USER_PIC, prefUserPic);
        editor.commit();
    }

    public String getFirstUserName() {
        return pref.getString(PREF_USER_NAME, "");
    }

    public String getFirstUserEmail() {
        return pref.getString(PREF_USER_EMAIL, "");
    }

    public String getFirstUserPic() {
        return pref.getString(PREF_USER_PIC, "");
    }

    //add task param
    public void setCheckedCategory(String checkedCategory) {
        editor.putString(PREF_CHECKED_CATEGORY, checkedCategory);
        editor.commit();
    }

    public String getCheckedCategory() {
        return pref.getString(PREF_CHECKED_CATEGORY, "");
    }

    public String getCheckedDate() {
        return pref.getString(PREF_CHECKED_DATE, "");
    }

    public void setCheckedDate(String checkedDate) {
        editor.putString(PREF_CHECKED_DATE, checkedDate);
        editor.commit();
    }
}