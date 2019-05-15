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
    public Student(String n, String s) {
        name = n;
        school = s;
        psessions = new ArrayList<Sessions>();
        usessions = new ArrayList<Sessions>();
    }
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

    public void removePsession(Sessions s) {
        for(int i = 0; i < psessions.size(); i++) {
            if(psessions.get(i).equals(s)) {
                psessions.remove(i);
                break;
            }
        }
    }
    public void removeUsession(Sessions s) { usessions.remove(s); }
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

    public boolean hasPsession(Sessions s) {
        for(int i = 0; i < psessions.size(); i++) {
            if(psessions.get(i).equals(s)) return true;
        }
        return false;
    }
    /**
     * Sets the school of the student to the inputted parameter (String
     * @param school String name of the school
     */
    public void setSchool(String school) {this.school = school;}
    public void setPsessions(ArrayList<Sessions> psessions) {this.psessions = psessions;}
    public void setUsessions(ArrayList<Sessions> usessions) {this.usessions = usessions;}
    @Override
    public String toString() {
        return name + " from " + school;
    }
}
