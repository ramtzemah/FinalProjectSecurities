package com.example.finalprojectsecurities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class MSPV {

    private final String SP_FILE = "SP_FILE08";


    private static MSPV me;
    private SharedPreferences sharedPreferences;

    public static MSPV getMe() {
        return me;
    }

    private MSPV(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
    }

    public static MSPV initHelper(Context context) {
        if (me == null) {
            me = new MSPV(context);
        }
        return me;
    }









    public void putDouble(String KEY, double defValue) {
        putString(KEY, String.valueOf(defValue));
    }

    public double getDouble(String KEY, double defValue) {
        return Double.parseDouble(getString(KEY, String.valueOf(defValue)));
    }

    public int getInt(String KEY, int defValue) {
        return sharedPreferences.getInt(KEY, defValue);
    }

    public void putInt(String KEY, int value) {
        sharedPreferences.edit().putInt(KEY, value).apply();
    }

    @SuppressLint("SuspiciousIndentation")
    public String getString(String KEY, String defValue) {
        if(sharedPreferences==null){
            return null;
        }else
        return sharedPreferences.getString(KEY, defValue);
    }

    public void putString(String KEY, String value) {
        sharedPreferences.edit().putString(KEY, value).apply();
    }


}