package com.example.admin.appbus1.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by giaqu on 1/4/2017.
 */

public class Bus extends RealmObject {
    @PrimaryKey
    private String id;
    private String way;
    private String time;
    private String frequency;
    private String price;
    private String go;
    private String back;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGo() {
        return go;
    }

    public void setGo(String go) {
        this.go = go;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }
}
