package com.example.voluntutor;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
//This class represents a tutor in the VolunTutor Application

public class Tutor {
    //data
    private String name;
    private String school;
    private ArrayList<Sessions> psessions;
    private ArrayList<Sessions> usessions;
    private ArrayList<Sessions> vsessions;
    private ArrayList<TimeSlot> timeSlots;
    private ArrayList<String> subjects;
    //constructor(s)
    public Tutor(String n, String s) {
        name = n;
        school = s;
        psessions = new ArrayList<Sessions>();
        usessions = new ArrayList<Sessions>();
        vsessions = new ArrayList<Sessions>();
        subjects = new ArrayList<String>();
        timeSlots = new ArrayList<TimeSlot>();
    }

    /**
     * Empty constructor
     */
    public Tutor() {
        name = "";
        school = "";
        psessions = new ArrayList<Sessions>();
        usessions = new ArrayList<Sessions>();
        vsessions = new ArrayList<Sessions>();
        subjects = new ArrayList<String>();
        timeSlots = new ArrayList<TimeSlot>();
    }
    //methods

    /**
     * Says if a specific string is among the tutor's subjects (case insensitive)
     * @param s the string being tested
     * @return if the string is, true, if not false
     */
    public boolean containsSub(String s) {
        for(int i = 0; i < subjects.size(); i++) {
            if(subjects.get(i).toLowerCase().equals(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    /**
     * removes a specific time slot from the tutor's time slot
     * @param s the time slot
     */
    public void removeSlot(TimeSlot s) { timeSlots.remove(s); }
    /**
     * Adds a subject to the list of subjects this person tutors in
     * @param s the subject to be added
     */
    public void addSubject(String s) {
        subjects.add(s);
    }
    /**
     * Takes a subject out of the list of subjects
     * @param s the subject to be removed
     */
    public void removeSubject(String s) {
        subjects.remove(s.toLowerCase());
    }

    /**
     * Gets the name of the tutor.
     * @return String representation of the tutor's name
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
     * Gets the pending sessions of the tutor.
     * @return ArrayList of Sessions that are pending
     */
    public ArrayList<Sessions> getPsessions() {
        return psessions;
    }
    public void setPsessions(ArrayList<Sessions> psessions) { this.psessions = psessions; }
    /**
     * Checks through the upcoming sessions to see if they have already passed. If they have
     * the session is moved to pending
     */
    public void checkUpcoming() {
        Calendar cal = Calendar.getInstance();
        for(int i = 0; i < usessions.size(); i++) {
            if(usessions.get(i).getDate().before(cal.getTime())) {
                Sessions s = usessions.get(i);
                psessions.add(s);
                usessions.remove(i);
                i--;
            }
        }
        Collections.sort(psessions);
    }
    /**
     * If any of the pending sessions have been fully verified, this method will move them to
     * the vsessions arraylist. If one of the pending sessions has not occurred, it get deleted.
     */
    public void checkPending() {
        for(int i = 0; i < psessions.size(); i++) {
            if(psessions.get(i).getVerified()) {
                Sessions s = psessions.get(i);
                psessions.remove(i);
                vsessions.add(s);
                i--;
            }
            else if(psessions.get(i).getHappened() != true) {
                psessions.remove(i);
            }
        }
        Collections.sort(vsessions);
    }
    /**
     * Gets the sessions that are upcoming
     * @return the sessions in an arraylist
     */
    public ArrayList<Sessions> getUsessions() {
        return usessions;
    }
    public void removeUsession(Sessions s) { usessions.remove(s); }
    public void setUsessions(ArrayList<Sessions> usessions) {
        this.usessions = usessions;
    }

    /**
     * Gets the sessions that are already verified
     * @return the sessions that are verified in an arraylist
     */
    public ArrayList<Sessions> getVsessions() {
        return vsessions;
    }

    public void setVsessions(ArrayList<Sessions> vsessions) {
        this.vsessions = vsessions;
    }

    /**
     * Adds a session to upcoming sessions
     * @param s the session
     */
    public void addSession(Sessions s) {
        /*Calendar cal = Calendar.getInstance();
        if(s.getDate().after(cal.getTime())) {*/
            usessions.add(s);
            Collections.sort(usessions);
        /*}*/
    }
    /**
     * Gets the Time Slots of the tutor.
     * @return ArrayList of Dates the tutor is available
     */
    public ArrayList<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(ArrayList<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    /**
     * Adds a time slot during which the tutor is available
     */
    public void addTimeSlots(TimeSlot t) {
        timeSlots.add(t);
    }
    /**
     * Gets the subjects the tutor tutors in
     * @return the subjects the tutor tutors in
     */
    public ArrayList<String> getSubjects() {
        return subjects;
    }
    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    /**
     * toString method that prints info about the tutor
     * @return informaiton about the tutor
     */
    @Override
    public String toString() {
        return name + " from " + school + " with time slot(s) at " + Arrays.toString(timeSlots.toArray())
                + " tutoring in " + Arrays.toString(subjects.toArray());
    }

    /**
     * Equals method for tutors
     * @param t the other tutor
     * @return if the tutors are the same
     */
    public boolean equals(Tutor t) {
        return name.equals(t.getName()) && school.equals(t.getSchool()) && psessions.equals(t.getPsessions()) && usessions.equals(t.getUsessions())
                && vsessions.equals(t.getVsessions()) && timeSlots.equals(t.getTimeSlots()) && subjects.equals(t.getSubjects());
    }
}
