package com.example.voluntutor;

import java.util.Date;

/**
 * Stores information about a tutoring session (date, location, length, verified status, tutor, and tutee)
 */
public class Sessions {
    //data
    private Date date;
    private String location;
    // length of session in minutes
    private int length;
    private boolean verified;
    private String tutor;
    private String tutee;
    //constructor(s)
    public Sessions(Date d, String l, int len, String tutor, String tutee) {
        date = d;
        location = l;
        len = length;
        verified = false;
        this.tutor = tutor;
        this.tutee = tutee;
    }
    //methods

    /**
     * Gets the date of the session
     * @return the date of the session
     */
    public Date getDate() {return date;}

    /**
     * Gets the location of the session
     * @return the location of the session
     */
    public String getLoc() {return location;}

    /**
     * Gets the length of time the session is (in minutes)
     * @return the length of the session in minutes
     */
    public int getLength() {return length;}

    /**
     * Gets whether the session is verified
     * @return whether the session is verified
     */
    public boolean getVerified() {return verified;}

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
     * Sets verified status to true
     */
    public void verify() {verified = true;}

    /**
     * Sets the date of the session
     * @param d the date of the session
     */
    public void setDate(Date d) {date = d;}

    /**
     * Sets the location of the session
     * @param loc the location of the session
     */
    public void setLocation(String loc) {location = loc;}

    /**
     * Sets the length of the session in minutes
     * @param len the length of the session in minutes
     */
    public void setLength(int len) {length = len;}

    /**
     * Sets the name of the tutor
     * @param t the name of the tutor
     */
    public void setTutor(String t) {tutor = t;}

    /**
     * Sets the name of the tutee
     * @param s the name of the tutee
     */
    public void setTutee(String s) {tutee = s;}
}
