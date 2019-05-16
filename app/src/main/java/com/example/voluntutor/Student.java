package com.example.voluntutor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * This class stores information about a student from the VolunTutor application
 */
public class Student {

    //Data
    private String name;
    private String school;
    private ArrayList<Sessions> psessions;
    private ArrayList<Sessions> usessions;

    //Constructor(s)

    /**
     * Constructs a student object with given name and school string parameters
     * @param n String name of student
     * @param s String school of student
     */
    public Student(String n, String s) {
        name = n;
        school = s;
        psessions = new ArrayList<Sessions>();
        usessions = new ArrayList<Sessions>();
    }

    /**
     * Default constructor of a Student object without parameters
     */
    public Student() {
        name = "";
        school = "";
        psessions = new ArrayList<Sessions>();
        usessions = new ArrayList<Sessions>();
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
    public String getSchool() {return school;}

    /**
     * Finds a session in the psessions list and sets it to sverified = true
     * @param s the session being changes
     */
    public void psessionSverify(Sessions s) {
        for(int i = 0; i < psessions.size(); i++) {
            if(psessions.get(i).equals(s)) {
                s.setSverified(true);
                psessions.set(i, s);
            }
        }
    }

    /**
     * Finds a session in the psessions list and sets it to tverified = true
     * @param s the session being changes
     */
    public void psessionTverify(Sessions s) {
        for(int i = 0; i < psessions.size(); i++) {
            if(psessions.get(i).equals(s)) {
                s.setTverified(true);
                psessions.set(i, s);
            }
        }
    }

    /**
     * Gets and returns the pending sessions
     * @return the sessions (ArrayList<Session>)the student has yet to verify
     */
    public ArrayList<Sessions> getPSessions() {return psessions;}

    /**
     * Remoces a session from the Pending Sessions ArrayLsit
     * @param s Session object to be removed
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
     * Adds a session to the Pending Sessions ArrayList
     * @param s Session object to be added
     */
    public void addPsession(Sessions s) { psessions.add(s); }

    /**
     * Gets and returns the upcoming sessions
     * @return the sessions (ArrayList<Session>)the student has not yet attended
     */
    public ArrayList<Sessions> getUSessions() {return usessions;}

    /**
     * Adds a session to upcoming sessions
     * @param s the session
     */
    public void addSession(Sessions s) {
        usessions.add(s);
        Collections.sort(usessions);
    }
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
     * Sets the name of the student to the inputted parameter (String)
     * @param name String name of the student
     */
    public void setName(String name) {this.name = name;}

    /**
     * Checks to see if a session object is part of the Pending Sessions ArrayList
     * @param s session object to be checked
     * @return if the session is part of the Pending Sessions ArrayList
     */
    public boolean hasPsession(Sessions s) {
        for(int i = 0; i < psessions.size(); i++) {
            if(psessions.get(i).equals(s)) return true;
        }
        return false;
    }

    /**
     * Updates the existing pending sessions ArrayList
     * @param psessions Pending Sessions ArrayList that needs to be updated
     */
    public void setPsessions(ArrayList<Sessions> psessions) {this.psessions = psessions;}

    /**
     * Updates the existing upcoming sessions ArrayList
     * @param usessions Upcoming Sessions ArrayList that needs to be updated
     */
    public void setUsessions(ArrayList<Sessions> usessions) {this.usessions = usessions;}

    /**
     * toString method for displaying Student object information
     * @return Student obejct name and school
     */
    @Override
    public String toString() {
        return name + " from " + school;
    }
}
