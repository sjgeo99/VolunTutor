package com.example.voluntutor;

import java.util.Date;

/**
 * Stores information about a tutoring session (date, location, length, verified status, tutor, and tutee)
 */
public class Sessions implements Comparable<Sessions> {
    //data
    private Date date;
    private String location;
    // length of session in minutes
    private int length;
    private boolean sverified;
    private boolean tverified;
    private boolean happened;
    private String tutor;
    private String tutee;
    //constructor(s)
    public Sessions(Date d, String l, int len, String tutor, String tutee) {
        date = d;
        location = l;
        length = len;
        sverified = false;
        tverified = false;
        happened = false;
        this.tutor = tutor;
        this.tutee = tutee;
    }
    /**
     * Empty constructor
     */
    public Sessions() {
        date = new Date();
        location = "here";
        length = 0;
        tutor = "me";
        tutee = "you";
    }
    //methods
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
     * Gets whether the session happened (note: by default, the happened boolean is false). It only
     * becomes true if one of the participants (tutor or student) changes it.
     * @return whether the session happened or not (as a boolean)
     */
    public boolean getHappened() { return happened; }
    public void setHappened(boolean b) { happened = b; }
    /**
     * Gets the date of the session
     * @return the date of the session
     */
    public Date getDate() {return date;}

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
     * Gets whether the session is verified
     * @return whether the session is verified
     */
    public boolean getVerified() {return sverified && tverified;}
    public boolean getSVerified() { return sverified; }
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
    public void tverify() {tverified = true;}
    public void setTverified(boolean b) { tverified = b; }

    /**
     * Sets the verified status to true for the student
     */
    public void sverify() {sverified = true;}
    public void setSverified(boolean b) { sverified = b; }
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

    /**
     * equals method for sessions
     * @param s the other session to compare to
     * @return if the 2 objects are the same
     */
    public boolean equals(Sessions s) {
        return date.equals(s.date) && location.equals(s.getLocation()) && length == s.getLength() && (sverified && tverified) == s.getVerified()
                && happened == s.getHappened() && tutor.equals(s.getTutor()) && tutee.equals(s.getTutee());
    }
}
