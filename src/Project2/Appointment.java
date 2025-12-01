/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project2;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private String appointmentType;
    private LocalDate date;
    private LocalTime time;
    private String note=null;
    
    public Appointment(String appointmentType,LocalDate date,LocalTime time,String note){
        setType(appointmentType);
        setDate(date);
        setTime(time);
        this.note=note;
    }
    
    public String getType(){
        return appointmentType;
    }
    public LocalDate getDate(){
        return date;
    }
    public LocalTime getTime(){
        return time;
    }
    public String getNote(){
        return note;
    }
    public void setType(String appointmentType){
        if (appointmentType == null || appointmentType.isBlank()) {
            throw new IllegalArgumentException("Appointment Type cannot be null or empty.");}
        this.appointmentType=appointmentType;
    }
    public void setDate(LocalDate date){
        if (date == null) {
            throw new IllegalArgumentException("Appointment Date cannot be null.");
        }
        // Business Logic: Appointment must be today or in the future
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Appointment date cannot be in the past: " + date);
        }
        this.date=date;
    }
    public void setTime(LocalTime time){
        if (time == null) {
            throw new IllegalArgumentException("Appointment Time cannot be null.");
        }
        this.time=time;
    }
    public void setNote(String note){
        this.note=note;
    }
    public String toString(){
        return "Appointment Type: "+this.appointmentType+
                "\nDate: "+this.date+
                "\nTime: "+this.time+
                "\nNote: "+this.note;
    }
    
}
