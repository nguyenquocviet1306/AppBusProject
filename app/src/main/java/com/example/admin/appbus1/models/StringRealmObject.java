package com.example.admin.appbus1.models;

import io.realm.RealmObject;

/**
 * Created by giaqu on 1/3/2017.
 */

public class StringRealmObject extends RealmObject {
    private String string;

    public StringRealmObject(String string) {
        this.string = string;
    }

    public StringRealmObject() {
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
