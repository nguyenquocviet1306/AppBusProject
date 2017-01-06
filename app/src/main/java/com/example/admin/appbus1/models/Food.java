package com.example.admin.appbus1.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by giaqu on 1/6/2017.
 */

public class Food extends RealmObject {
    @PrimaryKey private String id;
    private RealmList<StringRealmObject> foody;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RealmList<StringRealmObject> getFoody() {
        return foody;
    }

    public void setFoody(RealmList<StringRealmObject> foody) {
        this.foody = foody;
    }
}
