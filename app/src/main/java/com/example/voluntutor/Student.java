package com.example.voluntutor;

import java.util.ArrayList;
import java.util.Calendar;

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
     * Gets and returns the pending sessions
     * @return the sessions (ArrayList<Session>)the student has yet to verify
     */
    public ArrayList getPSessions() {return psessions;};

    /**
     * Gets and returns the upcoming sessions
     * @return the sessions (ArrayList<Session>)the student has not yet attended
     */
    public ArrayList getUSessions() {return usessions;};
    /**
     * Adds a session to upcoming sessions
     * @param s the session
     */
    public void addSession(Sessions s) {
        usessions.add(s);
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
    }
    /**
     * If any of the pending sessions have been fully verified, this method will delete them. If it
     * has not happened, it is also deleted.
     */
    public void checkPending() {
        for(int i = 0; i < psessions.size(); i++) {
            if(psessions.get(i).getVerified()) {
                psessions.remove(i);
                i--;
            }
            else if(psessions.get(i).isHappened() != true) {
                psessions.remove(i);
            }
        }
    }
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
