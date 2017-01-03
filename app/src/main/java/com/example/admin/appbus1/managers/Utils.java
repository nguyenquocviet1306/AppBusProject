package com.example.admin.appbus1.managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by giaqu on 1/3/2017.
 */

public class Utils {

    public static void setLoadData(Context context, String key, boolean isLoaded) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SP", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, isLoaded);
        editor.commit();
    }
}
