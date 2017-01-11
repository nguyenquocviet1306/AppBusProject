package com.example.admin.appbus1.managers;

import com.example.admin.appbus1.models.StringRealmObject;

/**
 * Created by Admin on 1/11/2017.
 */

public class EvenBusForUni {
    private StringRealmObject stringRealmObject;

    public EvenBusForUni(StringRealmObject stringRealmObject) {
        this.stringRealmObject = stringRealmObject;
    }

    public StringRealmObject getStringRealmObject() {
        return stringRealmObject;
    }

    public void setStringRealmObject(StringRealmObject stringRealmObject) {
        this.stringRealmObject = stringRealmObject;
    }
}
