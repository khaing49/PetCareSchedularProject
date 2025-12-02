/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project1;

import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.HashMap;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class EcoPointsRecyclingTracker {
    public static class NegativeNumberException extends Exception{
        public NegativeNumberException(){
            super( );
        }
    }
    public static void registerHousehold(Scanner scanner,HashMap <String,Household> households){
        System.out.println("**Save the world together**");
        System.out.print("Enter your ID: ");
        String id=scanner.nextLine().trim();
        if(households.containsKey(id)){
              System.out.println("Id already exit!!Please try again!!");
            }
        System.out.print("Enter household name: ");
        String name=scanner.nextLine().trim();
        System.out.print("Enter household address: ");
        String address = scanner.nextLine().trim();
        Household household=new Household(id,name,address);
        households.put(id, household);
        System.out.println("Household registered successfully on " + household.getJoinDate());       
    }
    public static void logRecyclingEvent(Scanner scanner,HashMap <String,Household> households){
        System.out.print("Enter household ID: ");
        String id = scanner.nextLine().trim();
        Household household = households.get(id);
        if (household == null) {
            System.out.println("Error: Household ID not found.");
            return;
        }
        System.out.print("Enter the material type: ");
        String materialType=scanner.nextLine();
        double weight=0;
        try{
        System.out.print("Enter the weight of the materal: ");
        weight=scanner.nextDouble();
        if(weight<=0){
            throw new NegativeNumberException();
        }
        }catch(NegativeNumberException e){
            System.out.print("The weight can't be negative!!");
        }
        RecyclingEvent event = new RecyclingEvent(materialType, weight);
        household.addEvent(event);
         System.out.println("Recycling event logged! Points earned: " + event.getEcoPoints());     
    }
    public static void displayHouseholds(HashMap <String,Household> households){
        if(households.isEmpty()){
            System.out.print("There is no household!!");
        }
        System.out.println("\nRegistered Households:");
        for(Household h:households.values()){
            System.out.print("ID: "+h.getId()+"\nName: "+h.getName()+
                    "\nAddress: "+h.getAddress()+"\nDate: "+h.getJoinDate());
            
        }
    }
    public static void displayHouseholdEvents(Scanner scanner,HashMap <String,Household> households){
        System.out.print("Enter household ID: ");
        String id = scanner.nextLine().trim();  
        Household household = households.get(id);
        if (household == null) {
            System.out.println("Household not found.");
            return;
        }
        System.out.println("\nRecycling Events for " + household.getName() + ":");
        if (household.getEvents().isEmpty()) {
            System.out.println("No events logged.");
        } else {
            for (RecyclingEvent e : household.getEvents()) {
                System.out.println(e);
            }
            System.out.println("Total Weight: " + household.getTotalWeight() + " kg");
            System.out.println("Total Points: " + household.getTotalPoints() + " pts");
        }
    }
    public static void generateReports(HashMap <String,Household> households){
        if (households.isEmpty()) {
            System.out.println("No households registered.");
            return;
        }
         Household top = null;
        for (Household h : households.values()) {
            if (top == null || h.getTotalPoints() > top.getTotalPoints()) {
                top = h;
            }
        }
         System.out.println("\nHousehold with Highest Points:");
        System.out.println("ID: " + top.getId() +
                           ", Name: " + top.getName() +
                           ", Points: " + top.getTotalPoints());
         double totalWeight = 0.0;
         for (Household h : households.values()) {
            totalWeight += h.getTotalWeight();
        }
        System.out.println("Total Community Recycling Weight: " + totalWeight + " kg");
}

    public static void saveHouseholdsToFile(HashMap <String,Household> households){
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("households.ser")
            );
            out.writeObject(households);
            out.close();
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        
        Scanner scanner= new Scanner(System.in);
        HashMap <String,Household> households=new HashMap<>();
        boolean running = true;
        while (running) {
            System.out.println("\n=== Eco-Points Recycling Tracker ===");
            System.out.println("1. Register Household");
            System.out.println("2. Log Recycling Event");
            System.out.println("3. Display Households");
            System.out.println("4. Display Household Recycling Events");
            System.out.println("5. Generate Reports");
            System.out.println("6. Save and Exit");
            System.out.print("Choose an option: ");       
        }
        String choice = scanner.nextLine();
        switch(choice){
            case "1":
                    registerHousehold(scanner,households);
                    break;
                case "2":
                    logRecyclingEvent(scanner,households);
                    break;
                case "3":
                    displayHouseholds(households);
                    break;
                case "4":
                    displayHouseholdEvents(scanner,households);
                    break;
                case "5":
                    generateReports(households);
                    break;
                case "6":
                    saveHouseholdsToFile(households);
                    running = false;
                    System.out.println("Data saved. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-6."); 
        }
    }
}
    

