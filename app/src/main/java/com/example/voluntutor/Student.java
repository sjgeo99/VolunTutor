package com.example.voluntutor;

import java.util.ArrayList;

public class Student {
    //data
    private String name;
    private String school;
    private ArrayList<Sessions> sessions;
    //constructor(s)
    public Student(String n, String s) {
        name = n;
        school = s;
        sessions = new ArrayList<Sessions>();
    }
    //methods
}
