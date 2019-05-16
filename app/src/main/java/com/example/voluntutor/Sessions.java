package com.example.voluntutor;

import java.util.Date;

/**
 * Stores information about a tutoring session (date, location, length, verified status, tutor, and tutee)
 */
public class Sessions implements Comparable<Sessions> {
    //data
    private String date;
    private String location;
    // length of session in minutes
    private int length;
    private boolean sverified;
    private boolean tverified;
    private String tutor;
    private String tutee;
    private boolean imTutor;
    //constructor(s)
    public Sessions(String d, String l, int len, String tutor, String tutee, boolean b) {
        date = d;
        location = l;
        length = len;
        sverified = false;
        tverified = false;
        this.tutor = tutor;
        this.tutee = tutee;
        imTutor = b;
    }
    /**
     * Empty constructor
     */
    public Sessions() {
        date = Long.toString(new Date().getTime());
        location = "here";
        length = 0;
        tutor = "me";
        tutee = "you";
    }
    //methods
    public boolean getImTutor() {
        return imTutor;
    }
    public void setImTutor(boolean b) {
        imTutor = b;
    }
    /**
     * Adds a comparator method for sorting lists
     */
    @Override
    public int compareTo(Sessions o) {
        return date.compareTo(o.getDate());
    }
    /**
     * An override for the toString method
     */
    @Override
    public String toString() {
        return "Session is at " + location + ", begins at " + date + ", lasts " + length +
                " minutes, and takes place between " + tutor + " and " + tutee;
    }
    /**
     * Gets the date of the session
     * @return the date of the session
     */
    public String getDate() {return date;}

    /**
     * Gets the location of the session
     * @return the location of the session
     */
    public String getLocation() {return location;}

    /**
     * Gets the length of time the session is (in minutes)
     * @return the length of the session in minutes
     */
    public int getLength() {return length;}

    /**
     * Gets whether the session is verified by the student
     * @return whether the session is verified by the student
     */
    public boolean getSVerified() { return sverified; }

    /**
     * Gets whether the session is verified by the tutor
     * @return whether the session is verified by tutor
     */
    public boolean getTVerified() { return tverified; }

    /**
     * Gets the name of the tutor
     * @return the name of the tutor
     */
    public String getTutor() {return tutor;}

    /**
     * Gets the name of the tutee
     * @return the name of the tutee
     */
    public String getTutee() {return tutee;}

    /**
     * Sets verified status to true for the tutor
     */

    public void setTverified(boolean b) { tverified = b; }

    /**
     * Sets the verified status to true for the student
     */

    public void setSverified(boolean b) { sverified = b; }
    /**
     * Sets the name of the tutor
     * @param t the name of the tutor
     */
    public void setTutor(String t) {tutor = t;}
    /**
     * equals method for sessions
     * @param s the other session to compare to
     * @return if the 2 objects are the same
     */
    public boolean equals(Sessions s) {
        return date.equals(s.getDate()) && location.equals(s.getLocation()) && length == s.getLength() && sverified == s.getSVerified()
                && tverified == s.getTVerified() && tutor.equals(s.getTutor()) && tutee.equals(s.getTutee()) && imTutor == s.getImTutor();
    }
}
