package com.example.admin.appbus1.managers;

import com.example.admin.appbus1.models.Bus;

/**
 * Created by Admin on 1/4/2017.
 */

public class EventBus {
    private Bus bus;

    public EventBus(Bus bus) {
        this.bus = bus;
    }

    public Bus getBus() {
        return bus;
    }
}
