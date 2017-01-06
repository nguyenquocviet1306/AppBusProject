package com.example.admin.appbus1.managers;

import com.example.admin.appbus1.models.FoodRealmObject;

/**
 * Created by Admin on 1/6/2017.
 */

public class EvenFood {
    private FoodRealmObject food;

    public EvenFood(FoodRealmObject food) {
        this.food = food;
    }

    public FoodRealmObject getFood() {
        return food;
    }
}
