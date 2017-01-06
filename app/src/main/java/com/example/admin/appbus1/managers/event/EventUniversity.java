package com.example.admin.appbus1.managers.event;


import com.example.admin.appbus1.models.University;

/**
 * Created by giaqu on 1/3/2017.
 */

public class EventUniversity {
    private University university;

    public EventUniversity(University university) {
        this.university = university;
    }

    public University getUniversity() {
        return university;
    }
}
