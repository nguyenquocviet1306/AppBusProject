package com.example.admin.appbus1;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by giaqu on 1/3/2017.
 */

public class MyApplication extends Application {

//    private static MyApplication myApplication = new MyApplication();
//
//    public static MyApplication getMyApplication() {
//        return myApplication;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Utils.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(getApplicationContext())
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

//    public void setLoadData(String key, boolean isLoaded) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean(key, isLoaded);
//        editor.commit();
//    }

}
