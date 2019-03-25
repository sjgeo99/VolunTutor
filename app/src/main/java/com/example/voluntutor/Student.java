package com.example.voluntutor;

import java.util.ArrayList;

/**
 * This class stores information about a student from the VolunTutor application
 */
public class Student {

    //Data
    private String name;
    private String school;
    private ArrayList<Sessions> sessions;

    //Constructor(s)
    public Student(String n, String s) {
        name = n;
        school = s;
        sessions = new ArrayList<Sessions>();
    }

    //Methods

    /**
     * Gets and returns the name of the chosen student
     * @return the name (String) of the chosen student
     */
    public String getName() {return name;}

    /**
     * Gets and returns the school of the chosen student
     * @return the school (String) of the chosen student
     */
    public String getSchool() {return name;}

    /**
     * Gets and returns the
     * @return the sessions (ArrayList<Session>)the student has registered for
     */
    public ArrayList getSessions() {return sessions;};

    /**
     * Sets the name of the student to the inputted parameter (String)
     * @param name String name of the student
     */
    public void setName(String name) {this.name = name;}

    /**
     * Sets the school of the student to the inputted parameter (String
     * @param school String name of the school
     */
    public void setSchool(String school) {this.school = school;}
}
