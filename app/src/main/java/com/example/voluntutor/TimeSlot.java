package com.example.voluntutor;

/**
 * This class models time slots that the tutor chooses to say they are available during.
 */
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//add a comparator method to allow timeSlots to be sorted based on day of week first, then time (time works but not
//day of week)

public class TimeSlot implements Comparable<TimeSlot>{
    private String dayOfWeek;
    /**
     * On a 24-hour system
     */
    private int sHour;
    private int sMinute;
    private int eHour;
    private int eMinute;
    //constructor(s)
    public TimeSlot(String d, int sh, int sm, int eh, int em) {
        dayOfWeek = d;
        sHour = sh;
        sMinute = sm;
        eHour = eh;
        eMinute = em;
    }
    //methods
    /**
     * This takes in a session to see if the session fits within the time slot
     * @param s the session
     * @return whether the session is in the time slot
     */
    public boolean isValid(Sessions s) {
        Date d = s.getDate();

        SimpleDateFormat df = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
        String dow = df.format(d);
        boolean onDay = (dow.equals(dayOfWeek));

        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR, sHour);
        c.set(Calendar.MINUTE, sMinute);
        Date startSlot = c.getTime();
        c.set(Calendar.HOUR, eHour);
        c.set(Calendar.MINUTE, eMinute);
        Date endSlot = c.getTime();

        c.setTime(d);
        c.add(Calendar.MINUTE, s.getLength());
        Date endSession = c.getTime();

        boolean startWithin = (d.after(startSlot));
        boolean endWithin = (endSession.before(endSlot));

        return onDay && startWithin && endWithin;
    }
    /**
     * Returns the day of the week of the slot (as a string)
     * @return day of the week of the slot
     */
    public String getDay() {
        return dayOfWeek;
    }
    /**
     * Returns the hour that the slot begins
     * @return the start hour
     */
    public int startHour() {
        return sHour;
    }
    /**
     * Returns the minute that the slot begins
     * @return the start minute
     */
    public int startMinute() {
        return sMinute;
    }
    /**
     * Returns the hour that the slot ends
     * @return the end hour
     */
    public int eHour() {
        return eHour;
    }
    /**
     * Returns the minute that the slot ends
     * @return the end minute
     */
    public int eMinute() {
        return eMinute;
    }
    /**
     * Creates a string from the slot (ie Monday from 2:30 to 3:30)
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("00");
        return dayOfWeek + " from " + sHour + ":" + df.format(sMinute) + " to " + eHour + ":" + df.format(eMinute);
    }
    /**
     * Allows ArrayLists of time slots to be sorted based on time
     */
    @Override
    public int compareTo(TimeSlot o) {
        int dayCompare = o.getDay().compareTo(dayOfWeek);
        if(dayCompare != 0) {
            return -1*dayCompare;
        }
        int hourCompare = Integer.compare(o.startHour(), sHour);
        if(hourCompare != 0) {
            return -1*hourCompare;
        }
        return -1*Integer.compare(o.startMinute(), sMinute);
    }
}
