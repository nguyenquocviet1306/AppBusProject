package com.example.admin.appbus1.managers;

import com.example.admin.appbus1.models.University;

import java.util.List;

import io.realm.Realm;


/**
 * Created by giaqu on 1/2/2017.
 */

public class RealmHandler {
    private static RealmHandler instance;
    private Realm realm;
    private RealmHandler(){
        this.realm = Realm.getDefaultInstance();
    }

    public static RealmHandler getInstance() {
        if (instance == null)
            instance = new RealmHandler();
        return instance;
    }

    public List<University> getUniversityFromRealm(){
        return realm.where(University.class).findAll();
    }

    public void addUniversityToRealm(University university){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(university);
        realm.commitTransaction();
    }

    public void clearUniversityInRealm(){
        realm.beginTransaction();
        realm.delete(University.class);
        realm.commitTransaction();
    }

}
