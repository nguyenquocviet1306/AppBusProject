package com.example.admin.appbus1.managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by giaqu on 1/3/2017.
 */

public class Utils {

    private SharedPreferences sharedPreferences;

    public static void setLoadData(Context context, String key, boolean isLoaded) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SP", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, isLoaded);
        editor.commit();
    }

    private Utils(Context context){
        sharedPreferences = context.getSharedPreferences("iStudent",Context.MODE_PRIVATE);
    }

    private static Utils instance;
    public static Utils getInstance(){
        return instance;
    }
    public static void init(Context context){
        instance = new Utils(context);
    }
}
