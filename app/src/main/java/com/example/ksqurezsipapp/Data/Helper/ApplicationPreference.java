package com.example.ksqurezsipapp.Data.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ksqurezsipapp.Data.Model.UserModel;
import com.google.gson.Gson;

public class ApplicationPreference {

    private Context context;
    private static ApplicationPreference INSTANCE;
    private static SharedPreferences userPreference;

    public static String USER_DETAILS = "USER_DETAILS";

    public static String USER_DATA = "USER_DATA";

    public static String NETWORK_TYPE = "NETWORK_TYPE";
    public static String USER_LOGIN = "USER_LOGIN";


    public ApplicationPreference(Context context) {
        userPreference = context.getSharedPreferences(USER_DETAILS, Context.MODE_PRIVATE);
    }

    //SAVE USER DETAILS
    public void saveUserDetails(UserModel userModel) {
        if (userPreference != null) {
            userPreference.edit().putString(USER_DATA, new Gson().toJson(userModel)).apply();
        }
    }

    //GET USER DETAILS
    public UserModel getUserDetails() {
        if (userPreference != null) {
            String savedValue = userPreference.getString(USER_DATA, null);
            if (Validators.isNullOrEmpty(savedValue)) return null;
            return new Gson().fromJson(savedValue, UserModel.class);
        } else return null;
    }

    //SAVE NETWORK TYPE
    public void saveNetworkType(String networkType) {
        if (userPreference != null) {
            userPreference.edit().putString(NETWORK_TYPE, networkType).apply();
        }
    }

    //GET NETWORK TYPE
    public String getNetworkType() {
        if (userPreference != null) {
            userPreference.getString(NETWORK_TYPE, null);
        }
        return userPreference.getString(NETWORK_TYPE, null);
    }

    //SAVE LOGIN STATUS
    public void saveIsLoggedIn(boolean isLoggedIn) {
        if (userPreference != null) {
            userPreference.edit().putBoolean(USER_LOGIN, isLoggedIn).apply();
        }
    }

    //GET LOGIN STATUS
    public boolean getIsLoggedIn() {
        if (userPreference != null) {
            userPreference.getBoolean(USER_LOGIN, false);
        }
        return userPreference.getBoolean(USER_LOGIN, false);
    }

    //CLEAR THE PREFERENCE
    public void getClearPreference() {
        if (userPreference != null) {
            userPreference.edit().clear().apply();
        }
    }
}
