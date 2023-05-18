package com.example.ksqurezsipapp.Data.Helper;

import android.util.Patterns;

public class Validators {
    public static boolean isNull(String string) {
        return string == null;
    }

    public static boolean isEmpty(String string) {
        return string.isEmpty();
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}