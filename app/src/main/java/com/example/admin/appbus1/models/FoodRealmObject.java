package com.example.admin.appbus1.models;

import java.text.Normalizer;

import io.realm.RealmObject;

/**
 * Created by Admin on 1/6/2017.
 */

public class FoodRealmObject extends RealmObject {
    private String name;
    private String address;
    private String image;
    private String time;
    private String price;
    private String nameWithoutUnicode;

    public FoodRealmObject(String name, String address, String image, String time, String price) {
        this.name = name;
        this.address = address;
        this.image = image;
        this.time = time;
        this.price = price;
    }



    public FoodRealmObject(){

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNameWithoutUnicode(){
        FoodRealmObject foodRealmObject = new FoodRealmObject();
        String nameWithoutUnicode = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replace("Đ", "D")
                .replace("đ", "d")
                .replaceAll("[^\\p{ASCII}]", "");
        foodRealmObject.setNameWithoutUnicode(nameWithoutUnicode);
        return nameWithoutUnicode;
    }

    public void setNameWithoutUnicode(String nameWithoutUnicode) {
        this.nameWithoutUnicode = nameWithoutUnicode;
    }
}
