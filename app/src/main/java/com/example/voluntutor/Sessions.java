package com.example.voluntutor;

import java.util.Date;

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
    public Date getDate() {return date;}
    public String getLoc() {return location;}
    public int getLength() {return length;}
    public boolean getVerified() {return verified;}
    public String getTutor() {return tutor;}
    public String getTutee() {return tutee;}
    public void verify() {verified = true;}
    public void setDate(Date d) {date = d;}
    public void setLocation(String loc) {location = loc;}
    public void setLength(int len) {length = len;}
    public void setTutor(String t) {tutor = t;}
    public void setTutee(String s) {tutee = s;}
}
