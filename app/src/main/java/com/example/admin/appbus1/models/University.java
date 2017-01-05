package com.example.admin.appbus1.models;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by giaqu on 12/14/2016.
 */

public class University extends RealmObject{

    @PrimaryKey private String name;
    private String id;
    private String abbreviation;
    private String logo;
    private String address;
    private RealmList<StringRealmObject> bus;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public RealmList<StringRealmObject> getBus() {
        return bus;
    }

    public void setBus(RealmList<StringRealmObject> bus) {
        this.bus = bus;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
