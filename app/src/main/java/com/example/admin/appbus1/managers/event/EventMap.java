package com.example.admin.appbus1.managers.event;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by giaqu on 1/14/2017.
 */

public class EventMap {
    private LatLng start;
    private LatLng end;

    public EventMap(LatLng start, LatLng end) {
        this.start = start;
        this.end = end;
    }

    public LatLng getStart() {
        return start;
    }

    public LatLng getEnd() {
        return end;
    }
}
