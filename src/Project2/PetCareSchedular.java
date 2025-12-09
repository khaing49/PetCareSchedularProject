/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.HashMap;

public class PetCareSchedular {
    
    public static void loadData(HashMap<String, Pet> allData){
        File file = new File("pet_data.txt");
        if (!file.exists()) {
        System.out.println("No saved data found. Starting with an empty list.");
        return;
        }
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] petAndAppointments = line.split("\\|", 2);
            String petData = petAndAppointments[0];
            String[] parts = line.split(",");
            final int num=7;
            if (parts.length >=num) {
                try{
                String id = parts[0];
                String petName = parts[1];
                String species = parts[2];
                double age = Double.parseDouble(parts[3]);
                String ownerName=parts[4];
                String contactInfo=parts[5];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate registrationDate = LocalDate.parse(parts[6], formatter);
                Pet pet = new Pet(id, petName, species, age, ownerName, contactInfo, registrationDate);
                if (petAndAppointments.length > 1) {
                    String appointmentData = petAndAppointments[1];           
                    String[] appointmentStrings = appointmentData.split("\\|"); 
                        for (String apStr : appointmentStrings) {
                            if (apStr.trim().isEmpty()) continue;
                            String[] apParts = apStr.split(",", 4);
                            if (apParts.length == 4) {
                                String type = apParts[0];
                                LocalDate date = LocalDate.parse(apParts[1], dateFormat);
                                LocalTime time = LocalTime.parse(apParts[2], timeFormat);
                                String note = apParts[3].replace(";", ","); 
                                Appointment appointment = new Appointment(type, date, time, note);
                                pet.addAppointment(appointment);
                            } else {
                                System.err.println("Skipping malformed appointment data: " + apStr);
                            }
                        }
                    }
                    allData.put(id, pet);  
                } catch (NumberFormatException e) {
                    System.err.println("Skipping pet due to invalid number/age format: " + parts[3]);
                    e.printStackTrace();
                } catch (DateTimeParseException e) {
                    System.err.println("Skipping pet due to invalid date/time format in record: " + line);
                    e.printStackTrace();
                }
            } else {
                 System.err.println("Skipping malformed pet data line: " + line);
            }
        }
        System.out.println("Pet data loaded successfully.");
    } catch (IOException e) {
        System.out.println("An error occurred while reading from the file.");
        e.printStackTrace();
    }
}
    

    public static void registerThePet(Scanner scanner, HashMap<String, Pet> allData) {
            System.out.println("**Register your pet**");
            String id;
            do {
                System.out.print("Enter pet ID: ");
                id = scanner.nextLine().trim().toLowerCase();
                if (allData.containsKey(id)) {
                    System.out.print("That ID is already exit.Choose Another one!");
                }
            } while (allData.containsKey(id));
            System.out.println("Enter pet name: ");
            String petName = scanner.nextLine();
            System.out.println("Enter the species: ");
            String species = scanner.nextLine();
            System.out.print("Enter pet age: (1 or 1.5 etc...) ");
            double age = scanner.nextDouble();
            scanner.nextLine();
            while (age <= 0) {
                System.out.print("Age can't be zero or negative!!");
                System.out.print("Enter pet age: (1 or 1.5 etc...) ");
                age = scanner.nextDouble();
                scanner.nextLine();
            }
            System.out.println("Enter the owner name: ");
            String ownerName = scanner.nextLine();
            System.out.println("Enter the contact info:(phone-number) ");
            String contactInfo = scanner.nextLine();
            LocalDate registrationDate = null;
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            boolean dateParsedSuccessfully=false;
            do{
            System.out.println("Enter the registeration date in MM/dd/yyyy format :");
            String inputDate = scanner.nextLine();
            try{
            registrationDate = LocalDate.parse(inputDate, dateFormat);
            if (registrationDate.isAfter(LocalDate.now())) {
            System.out.println("Registration date cannot be in the future. Please enter today's date or a past date.");
            registrationDate = null;
            } else {
            dateParsedSuccessfully=true;
            }
            }catch(java.time.format.DateTimeParseException dfe){
              System.out.println("Incorrect format. Please use the MM/dd/yyyy format."); 
}           }
            while(!dateParsedSuccessfully);
            Pet pet = new Pet(id, petName, species, age, ownerName, contactInfo, registrationDate);
            allData.put(id, pet);
            storeToFile(allData);
            System.out.println("Pet registered successfully!");
         }   
               
    public static void scheduleAnAppointment(Scanner scanner, HashMap<String, Pet> allData) {
        System.out.println("**Schedule an appointment**");
        try {
            System.out.print("Enter pet ID: ");
            String id = scanner.nextLine().trim().toLowerCase();
            Pet pet = allData.get(id.toLowerCase());
            if (pet == null) {
                System.out.print("No ID found!Please register first!!");
                registerThePet(scanner, allData);
            }
            System.out.print("Enter the reason to appointment(such as vet visit, vaccination, grooming): ");
            String appointmentType = scanner.nextLine();
            LocalDate date = null;
            LocalTime time = null;
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
            String inputDate;
            boolean dateParsedSuccessfully=false;
            do {
                System.out.println("Enter the appointment date in MM/dd/yyyy format :");
                inputDate = scanner.nextLine();
               try{
                date = LocalDate.parse(inputDate, dateFormat);
                if (date.isBefore(LocalDate.now())) {
                    System.out.println("Appointment date cannot be in the past!Please choose another date!");
                    date=null;
                }
                else{
                   dateParsedSuccessfully=true; 
                }
               }catch(java.time.format.DateTimeParseException dfe){
                   System.out.println("Incorrect format. Please use the MM/dd/yyyy format.");
               }
            } while (!dateParsedSuccessfully);
            boolean timeParsedSuccessfully=false;
            do{
            System.out.println("Enter the appointment time in HH:mm:ss format :(12:30:00) ");
            String inputTime = scanner.nextLine();
            try{
            time = LocalTime.parse(inputTime, timeFormat);
            timeParsedSuccessfully=true;
            }catch(java.time.format.DateTimeParseException dfe){
                System.out.println("Incorrect time format. Please use the HH:mm:ss format: (12:30:00) ");
            }
            } while (!timeParsedSuccessfully);
            System.out.print("Enter any additional notes: ");
            String note = scanner.nextLine();
            Appointment appointment = new Appointment(appointmentType, date, time, note);
            pet.addAppointment(appointment);
            storeToFile(allData);
            System.out.print("Scheduled successfully");
        } catch (Exception e) {
           System.out.println("An unexpected error occurred during scheduling.");
           e.printStackTrace(); 
        }
    }

    public static void storeToFile(HashMap<String, Pet> allData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pet_data.txt"))) {
        for (Pet p : allData.values()) {
        writer.write(p.toFileString());
        writer.newLine();
        System.out.println("Successfully wrote to the file.");
        }
        }catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public static void displayDetailsOfPets(Scanner scanner, HashMap<String, Pet> allData) {
        System.out.println("**Display the record**");
        System.out.println("1: All registered pets");
        System.out.println("2: All appointments for a specific pet");
        System.out.println("3: Upcoming appointments for all pets");
        System.out.println("4: Past appointment history for each pet");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                if (allData.isEmpty()) {
                    System.out.print("There is no pet in the list!!");
                }
                System.out.println("\nAll Registered Pets :");
                for (Pet p : allData.values()) {
                    System.out.print("ID: " + p.getId() + "\nPet Name: " + p.getPetName()
                            + "\nSpecies: " + p.getSpecies() + "\nAge: " + p.getAge() + "\nOwner Name: " + p.getOwnerName()+"\n");
                    System.out.println("------------------------");
                }
                break;
            case "2":
                System.out.print("Enter Pet ID to view appointments: ");
                String id = scanner.nextLine();
                Pet pet = allData.get(id);
                if (pet == null) {
                    System.out.println("No pet found with that ID!");
                } else if (pet.getAppointments().isEmpty()) {
                    System.out.println("There are no appointments for this pet.");
                } else {
                    System.out.println("\nAppointments for " + pet.getPetName() + ":");
                    for (Appointment ap : pet.getAppointments()) {
                        System.out.println("Type: " + ap.getType());
                        System.out.println("Date: " + ap.getDate());
                        System.out.println("Time: " + ap.getTime());
                        System.out.println("Notes: " + ap.getNote());
                        System.out.println("------------------------------------");
                    }
                }
                break;
            case "3":
                System.out.println("Upcoming Appointments for All Pets");
                LocalDate today = LocalDate.now();
                boolean found = false;
                for (Pet p : allData.values()) {
                    for (Appointment ap : p.getAppointments()) {
                        if (ap.getDate().isAfter(today)) {
                            found = true;
                            System.out.println("Pet ID: " + p.getId());
                            System.out.println("Pet Name: " + p.getPetName());
                            System.out.println("Appointment Type: " + ap.getType());
                            System.out.println("Date: " + ap.getDate());
                            System.out.println("Time: " + ap.getTime());
                            System.out.println("Notes: " + ap.getNote());
                        }
                    }
                }
                if (!found) {
                    System.out.println("There is no upcoming appointment!");
                }
                break;
            case "4":
                System.out.println("Past appointment history for each pet");
                LocalDate now = LocalDate.now();
                boolean pastAp = false;
                for (Pet p : allData.values()) {
                    for (Appointment ap : p.getAppointments()) {
                        if (ap.getDate().isBefore(now)) {
                            pastAp = true;
                            System.out.println("Pet ID: " + p.getId());
                            System.out.println("Pet Name: " + p.getPetName());
                            System.out.println("Appointment Type: " + ap.getType());
                            System.out.println("Date: " + ap.getDate());
                            System.out.println("Time: " + ap.getTime());
                            System.out.println("Notes: " + ap.getNote());
                        }
                    }
                }
                if (!pastAp) {
                    System.out.println("There is no upcoming appointment!");
                }
                break;
            default:
                System.out.println("Invalid choice. Please select 1-4.");
        }

    }

    public static void generateReports(Scanner scanner, HashMap<String, Pet> allData) {
        System.out.println("**Generating report**");
        System.out.println("1: Pets with upcoming appointments in the next week");
        System.out.println("2: Pets overdue for a vet visit ");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                LocalDate today = LocalDate.now();
                LocalDate nextWeek = today.plusWeeks(1);
                System.out.println("\nPets with Appointments in the Next 7 Days:");
                boolean found = false;
                for (Pet p : allData.values()) {
                    for (Appointment ap : p.getAppointments()) {
                        LocalDate apDate = ap.getDate();
                        if (!apDate.isBefore(today) && !apDate.isAfter(nextWeek)) {
                            found = true;
                            System.out.println("----------------------------------");
                            System.out.println("Pet ID: " + p.getId());
                            System.out.println("Pet Name: " + p.getPetName());
                            System.out.println("Appointment Type: " + ap.getType());
                            System.out.println("Date: " + ap.getDate());
                            System.out.println("Time: " + ap.getTime());
                            System.out.println("Notes: " + ap.getNote());
                        }
                    }
                }
                if (!found) {
                    System.out.println("No pets have appointments in the next week.");
                }
                break;
            case "2":
                LocalDate todayDate = LocalDate.now();
                System.out.println("\nPets Overdue for a Vet Visit:");
                boolean visited = false;
                for (Pet p : allData.values()) {
                    LocalDate lastVetVisit = null;
                    boolean hasUpcomingVetVisit = false;
                    for (Appointment ap : p.getAppointments()) {
                        if (ap.getType().equalsIgnoreCase("vet visit")) {
                            if (ap.getDate().isAfter(todayDate)) {
                                hasUpcomingVetVisit = true;
                                break;
                            }
                            if (lastVetVisit == null || ap.getDate().isAfter(lastVetVisit)) {
                                lastVetVisit = ap.getDate();
                            }
                        }
                    }
                    if (!hasUpcomingVetVisit && (lastVetVisit == null || lastVetVisit.isBefore(todayDate))) {
                        visited = true;
                        System.out.println("----------------------------------");
                        System.out.println("Pet ID: " + p.getId());
                        System.out.println("Pet Name: " + p.getPetName());
                        System.out.println("Last Vet Visit: "
                                + (lastVetVisit != null ? lastVetVisit : "No record found"));
                    }
                }
                if (!visited) {
                    System.out.println("All pets are up to date with vet visits!");
                }
                break;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashMap<String, Pet> allData = new HashMap<>();
        loadData(allData);
        boolean running = true;
        while (running) {
            System.out.println("\n=== PetCare Schedular ===");
            System.out.println("1. Register a Pet");
            System.out.println("2. Schedule an appointment");
            System.out.println("3. Store the details in a file");
            System.out.println("4. Display details of pets");
            System.out.println("5. Generate reports");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    registerThePet(scanner, allData);
                    break;
                case "2":
                    scheduleAnAppointment(scanner, allData);
                    break;
                case "3":
                    storeToFile(allData);
                    break;
                case "4":
                    displayDetailsOfPets(scanner, allData);
                    break;
                case "5":
                    generateReports(scanner, allData);
                    break;
                case "6":
                    System.out.println("Exiting program... Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-6.");

            }
        }
    }
}
