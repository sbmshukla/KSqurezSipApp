package com.example.ksqurezsipapp.Data.Helper

import android.content.Context
import android.content.SharedPreferences
import com.example.ksqurezsipapp.Data.Model.UserModel
import com.google.gson.Gson

class MyApplicationPreference(context: Context) {


    var USER_FILE = "USER_FILE"

    var USER_MODEL = "USER_MODEL"
//    var NETWORK_TYPE = "NETWORK_TYPE"
    var IS_LOGIN = "IS_LOGIN"

    var gson = Gson()

    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(USER_FILE, Context.MODE_PRIVATE)
    lateinit var editor: SharedPreferences.Editor

    fun saveUserModel(userModel: UserModel) {
        editor = sharedPreferences.edit()
        editor.putString(USER_MODEL, gson.toJson(userModel))
        editor.apply()
        editor.commit()
    }

    fun getUserModel(): String {

        val userData = sharedPreferences.getString(USER_MODEL, null)
        if (userData != null) {
            return userData
        }
        return ""

    }

    fun saveUserLogin(loginStatus: Boolean) {
        editor = sharedPreferences.edit()
        editor.putBoolean(IS_LOGIN, loginStatus)
        editor.apply()
        editor.commit()
    }

    fun getUserLogin(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGIN, false)
    }
}