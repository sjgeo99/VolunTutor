package com.example.voluntutor;

import java.util.ArrayList;
import java.util.Date;
//This class represents a tutor in the VolunTutor Application.
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

    public void addSubject(String s) { subjects.add(s); }
    public void removeSubject(String s) {
        subjects.remove(s);
    }

    /**
     * Gets the name of the tutor.
     * @return String representation
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name of the tutor.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets the school of the tutor.
     * @return String representation of school.
     */
    public String getSchool() {
        return school;
    }
    /**
     * Sets the name of the tutor.
     */
    public void setSchool(String school) {
        this.school = school;
    }
    /**
     * Gets the sessions of the tutor.
     * @return ArrayList of Sessions
     */
    public ArrayList<Sessions> getSessions() {
        return sessions;
    }

    /**
     * Gets the Time Slots of the tutor.
     * @return ArrayList of Dates
     */
    public ArrayList<Date> getTimeSlots() {
        return timeSlots;
    }
    /**
     * Gets the name of the tutor.
     * @return String representation
     */
    public void addTimeSlots(ArrayList<Date> timeSlots) {
        this.timeSlots = timeSlots;
    }
    /**
     * Gets the name of the tutor.
     * @return String representation
     */
    public ArrayList<String> getSubjects() {
        return subjects;
    }
    /**
     * Gets the name of the tutor.
     * @return String representation
     */
    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }
}
