package com.example.ksqurezsipapp.Data.Helper;

import androidx.multidex.MultiDexApplication;

public class MyApplications extends MultiDexApplication {

    private static MyApplications ourInstance;
    private ApplicationPreference myPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
        myPreferences = new ApplicationPreference(this);
    }

    public static MyApplications getInstance() {
        return ourInstance;
    }

    public ApplicationPreference getMyPreferencesInstance() {
        return myPreferences;
    }

}
