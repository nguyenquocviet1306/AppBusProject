package com.example.admin.appbus1.managers;

import com.example.admin.appbus1.models.Bus;
import com.example.admin.appbus1.models.University;

import java.util.ArrayList;
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

    public ArrayList<String> getNumberList(University university){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < university.getBus().size(); i++){
            String number = university.getBus().get(i).getString();
            list.add(number);
        }
        return list;
    }

    public String getDetailBus(String busNumber){
        Bus bus = realm.where(Bus.class).equalTo("id", busNumber).findFirst();
        String detail = bus.getId() + "\n" +
                bus.getWay() + "\n" +
                bus.getTime() + "\n" +
                bus.getFrequency() + "\n" +
                bus.getPrice() + "\n" +
                bus.getGo() + "\n" +
                bus.getBack();
        return detail;

    }

    public List<Bus> getBusFromRealm(){
        return realm.where(Bus.class).findAll();
    }

    public void addBusToRealm(Bus bus){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(bus);
        realm.commitTransaction();
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
    public void clearBusInRealm(){
        realm.beginTransaction();
        realm.delete(Bus.class);
        realm.commitTransaction();
    }

}
