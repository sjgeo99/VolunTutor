package com.example.voluntutor;

import java.util.ArrayList;
import java.util.Date;

public class Tutor {
    //data
    private String name;
    private String school;
    private ArrayList<Sessions> sessions;
    private ArrayList<Date> timeSlots;
    private ArrayList<String> subjects;
    //constructor(s)
    public Tutor(String n, String s, ArrayList<String> subs) {
        name = n;
        school = s;
        sessions = new ArrayList<Sessions>();
        subjects = subs;
    }
    //methods
    public void addSubject(String s) {
        subjects.add(s);
    }
    public void removeSubject(String s) {
        subjects.remove(s);
    }
}
