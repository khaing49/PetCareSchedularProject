/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project2;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class Pet {
    private String id;
    private String petName;
    private String species;
    private double age;
    private String ownerName;
    private String contactInfo;
    private LocalDate registrationDate;
    private List<Appointment>appointments;
    
    public Pet(String id,String petName,String species,double age,String ownerName,String contactInfo,LocalDate registrationDate){
        setId(id);
        setPetName(petName);
        setSpecies(species);
        setAge(age);
        setOwnerName(ownerName);
        setContactInfo(contactInfo);
        setRegistrationDate(registrationDate);
        this.appointments=new ArrayList<>();
    }
    public String getId(){ 
        return id;
    }
    public String getPetName(){
        return petName;
    }
    public String getSpecies(){
        return species;
    }
    public double getAge(){
        return age;
    }
    public String getOwnerName(){
        return ownerName;
    }
    public String getContactInfo(){
        return contactInfo;
    }
    public LocalDate getRegistrationDate(){
        return registrationDate;
    }
    public List<Appointment> getAppointments() {
        return appointments;
    }
    public void addAppointment(Appointment newAppointment){
        if (newAppointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null.");
        }
        this.appointments.add(newAppointment);
    }
    public void setId(String id){
         if (id == null || id.isBlank()) throw new IllegalArgumentException("Pet ID cannot be null or empty.");
        this.id=id;
    }
    public void setPetName(String petName){
        if (petName == null || petName.isBlank()) throw new IllegalArgumentException("Pet name cannot be null or empty.");
        this.petName=petName;
    }
    public void setSpecies(String species){
        if (species == null || species.isBlank()) throw new IllegalArgumentException("Species cannot be null or empty.");
        this.species=species;
    }
    public void setAge(double age){ 
        if (age < 0) throw new IllegalArgumentException("Age cannot be negative.");
        this.age=age;
    }
    public void setOwnerName(String ownerName){
        if (ownerName == null || ownerName.isBlank()) throw new IllegalArgumentException("Owner name cannot be null or empty.");
        this.ownerName=ownerName;
    }
    public void setContactInfo(String contactInfo){
        if (contactInfo == null || contactInfo.isBlank()) throw new IllegalArgumentException("Contact info cannot be null or empty.");
        this.contactInfo=contactInfo;
    }
    public void setRegistrationDate(LocalDate registrationDate){
        if (registrationDate == null){
            throw new IllegalArgumentException("Registration date cannot be null.");
        }
        if (registrationDate.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Registration date cannot be in the future: " + registrationDate);
        }
        this.registrationDate=registrationDate;
    }
    
    public String toString(){
        return "ID: "+id+
               "\nPet Name: "+petName+
               "\nSpecies: "+species+
               "\nAge: "+age+
               "\nOwner Name: "+ownerName+
               "\nContact Info: "+contactInfo+
               "\nRegistration Date: "+registrationDate+
               "\nAppointments: "+appointments;
    }
    
    public String toFileString() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(",")
          .append(petName).append(",")
          .append(species).append(",")
          .append(age).append(",")
          .append(ownerName).append(",")
          .append(contactInfo).append(",")
          .append(registrationDate.format(dateFormat));
        
        // 2. Append Appointments, using '|' as the separator
        for (Appointment ap : appointments) {
            // Assumes Appointment class has a toFileString() method
            sb.append("|").append(ap.toFileString());
        }
        
        return sb.toString();
    }
    
}

