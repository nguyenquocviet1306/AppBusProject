package com.example.admin.appbus1.managers;

import com.example.admin.appbus1.models.Bus;
import com.example.admin.appbus1.models.Food;
import com.example.admin.appbus1.models.FoodRealmObject;
import com.example.admin.appbus1.models.StringRealmObject;
import com.example.admin.appbus1.models.University;

import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;


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

    public List<Food> getFoodFromRealm(){
        return realm.where(Food.class).findAll();
    }

    public List<FoodRealmObject> getFoodFromRealmObject(){
        return realm.where(FoodRealmObject.class).findAll();
    }

    public RealmList<StringRealmObject> getNumberList(University university){
        RealmList<StringRealmObject> list = new RealmList<>();
        for(int i = 0; i < university.getBus().size(); i++){
            StringRealmObject number = university.getBus().get(i);
            list.add(number);
        }
        return list;
    }

    public Bus getDetailBus(String busNumber){
//        realm.where(Bus.class).findAll();
        Bus bus = realm.where(Bus.class).equalTo("id", busNumber).findFirst();
        return bus;

    }




    public List<Bus> getBusFromRealm(){
        return realm.where(Bus.class).findAll();
    }

    public void addBusToRealm(Bus bus){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(bus);
        realm.commitTransaction();
    }

    public void addFoodToRealm(Food food){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(food);
        realm.commitTransaction();
    }


    public void addUniversityToRealm(University university){
        realm.beginTransaction();
        realm.copyToRealm(university);
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
    public void clearFoodInRealm(){
        realm.beginTransaction();
        realm.delete(FoodRealmObject.class);
        realm.commitTransaction();
    }

    public List<University> findUniversityByName(String searchString) {
        return realm.where(University.class)
                .contains("nameWithoutUnicode", searchString, Case.INSENSITIVE)
                .findAll();
    }

    public List<Bus> findBusById(String searchString) {
        return realm.where(Bus.class)
                .contains("id", searchString, Case.INSENSITIVE)
                .or()
                .contains("wayWithout", searchString, Case.INSENSITIVE)
                .findAll();
    }

    public List<FoodRealmObject> findFoodByName(String searchString) {
        return realm.where(FoodRealmObject.class)
                .contains("nameWithoutUnicode", searchString, Case.INSENSITIVE)
                .findAll();
    }
}
