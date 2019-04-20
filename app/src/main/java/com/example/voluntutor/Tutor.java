package com.example.voluntutor;

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
    //methods

    /**
     * Adds a subject to the list of subjects this person tutors in
     * @param s the subject to be added
     */
    public void addSubject(String s) {
        subjects.add(s.toLowerCase());
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
    public ArrayList<Sessions> getPSessions() {
        return psessions;
    }

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
            else if(psessions.get(i).isHappened() != true) {
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

    /**
     * Gets the sessions that are already verified
     * @return the sessions that are verified in an arraylist
     */
    public ArrayList<Sessions> getVsessions() {
        return vsessions;
    }
    /**
     * Adds a session to upcoming sessions
     * @param s the session
     */
    public void addSession(Sessions s) {
        usessions.add(s);
        Collections.sort(usessions);
    }
    /**
     * Gets the Time Slots of the tutor.
     * @return ArrayList of Dates the tutor is available
     */
    public ArrayList<TimeSlot> getTimeSlots() {
        return timeSlots;
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

    /**
     * toString method that prints info about the tutor
     * @return informaiton about the tutor
     */
    @Override
    public String toString() {
        return name + " from " + school + " with time slot(s) at " + Arrays.toString(timeSlots.toArray())
                + " tutoring in " + Arrays.toString(subjects.toArray());
    }
}
