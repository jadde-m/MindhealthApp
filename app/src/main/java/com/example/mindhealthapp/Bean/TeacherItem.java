package com.example.mindhealthapp.Bean;

public class TeacherItem {
    private Appointment appointment;
    private String name;
    public TeacherItem(Appointment appointment,String name){
        this.appointment = appointment;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
