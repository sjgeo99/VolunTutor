/*package com.example.voluntutor;

import java.util.ArrayList;
public class UserInformation {
    private Student stu;
    private Tutor tut;
    public UserInformation(Student student){
        stu = student;
    }

    public UserInformation(Tutor tutor){
        tut = tutor;
    }

    public UserInformation(){

    }
    public String getName() {
        if(tut == null){
            return stu.getName();
        }
        if(stu == null){
            return tut.getName();
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public ArrayList<Sessions> getPsessions() {
        return psessions;
    }

    public void setPsessions(ArrayList<Sessions> psessions) {
        this.psessions = psessions;
    }

    public ArrayList<Sessions> getUsessions() {
        return usessions;
    }

    public void setUsessions(ArrayList<Sessions> usessions) {
        this.usessions = usessions;
    }

    public ArrayList<Sessions> getVsessions() {
        return vsessions;
    }

    public void setVsessions(ArrayList<Sessions> vsessions) {
        this.vsessions = vsessions;
    }

    public ArrayList<String> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(ArrayList<String> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }
}
*/