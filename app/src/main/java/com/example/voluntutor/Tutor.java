package com.example.voluntutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

    /**
     * Constructs a Tutor object with specified parameters
     * @param n name (String)
     * @param s school (String)
     */
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
     * Default constructor, constructs Tutor object without any parameters
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
     * Checks to see if
     * @param s
     * @return
     */
    public boolean hasPsession(Sessions s) {
        for(int i = 0; i < psessions.size(); i++) {
            if(psessions.get(i).equals(s)) return true;
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
        Collections.sort(subjects);
    }
    /**
     * Takes a subject out of the list of subjects
     * @param s the subject to be removed
     */
    public void removeSubject(String s) {
        for(int i = 0; i < subjects.size(); i++) {
            if(subjects.get(i).equals(s)) {
                subjects.remove(i);
            }
        }
    }

    /**
     * Checks to see if a Tutor possesses a chosen TimeSlot
     * @param t TimeSlot chosen by user
     * @return whether or not a Tutor object contains the particular TimeSlot
     */
    public boolean hasTs(TimeSlot t) {
        for(int i = 0; i < timeSlots.size(); i++) {
            if(timeSlots.get(i).equals(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a TimeSlot from a Tutor object
     * @param t TimeSlot that needs to be removed
     */
    public void removeTimeSlot(TimeSlot t) {
        for(int i = 0; i < timeSlots.size(); i++) {
            if(timeSlots.get(i).equals(t)) timeSlots.remove(i);
        }
    }

    /**
     * Adds a Session object to the pending sessions list
     * @param s session object
     */
    public void addPsession(Sessions s) {
        psessions.add(s);
        Collections.sort(psessions);
    }

    /**
     *
     * @param s
     */
    public void removePsession(Sessions s) {
        for(int i = 0; i < psessions.size(); i++) {
            if(psessions.get(i).equals(s)) {
                psessions.remove(i);
                break;
            }
        }
    }

    /**
     * Gets the name of the tutor.
     * @return String representation of the tutor's name
     */
    public String getName() {
        return name;
    }

    /**
     * Checks to see if a session belongs to any Session-containing list
     * @param s session object
     * @return whether or not it belongs to upcoming, pending, or verified sessions lists
     */
    public boolean hasSession(Sessions s) {
        return usessions.contains(s) || psessions.contains(s) || vsessions.contains(s);
    }

    /**
     * Checks to see if upcoming sessions ArrayList contains a certain session
     * @param s session object
     * @return whether or not the upcoming sessions ArrayList contains the specified session
     */
    public boolean hasUsession(Sessions s) {
        for(int i = 0; i < usessions.size(); i++) {
            if(usessions.get(i).equals(s)) return true;
        }
        return false;
    }

    /**
     *
     * @param s
     * @return
     */
    public boolean hasSubject(String s) {
        for(int i = 0; i < subjects.size(); i++) {
            if(subjects.get(i).equals(s)) {
                return true;
            }
        }
        return false;
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
            Date d = new Date(usessions.get(i).getDate());
            if(d.before(cal.getTime())) {
                Sessions s = usessions.get(i);
                psessions.add(s);
                usessions.remove(i);
                i--;
            }
        }
        Collections.sort(psessions);
    }

    /**
     * Gets the sessions that are upcoming
     * @return the sessions in an arraylist
     */
    public ArrayList<Sessions> getUsessions() {
        return usessions;
    }

    /**
     * Removes an Upcoming Session from the upcoming sessions ArrayList
     * @param s session object
     */
    public void removeUsession(Sessions s) { usessions.remove(s); }
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
        Date d = new Date(Long.parseLong(s.getDate()));
        Calendar cal = Calendar.getInstance();
        if(d.after(cal.getTime())) {
            usessions.add(s);
            Collections.sort(usessions);
        }
        else {
            psessions.add(s);
            Collections.sort(psessions);
        }
    }

    /**
     * Adds a session object to the Upcoming Session ArrayList, and sorts the ArrayList
     * @param s session object to be added
     */
    public void addUsession(Sessions s) {
        usessions.add(s);
        Collections.sort(usessions);
    }

    /**
     * Sets the Upcoming Sessions ArrayList to a specified parameter ArrayList
     * @param s ArrayList of Sessions
     */
    public void setUsessions(ArrayList<Sessions> s) {
        usessions = s;
    }

    /**
     * Adds a session object to the Pending Session ArrayList, and sorts the ArrayList
     * @param s
     */
    public void addVsession(Sessions s) {
        vsessions.add(s);
        Collections.sort(vsessions);
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
        Collections.sort(timeSlots);
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
