🐾 PetCare Schedular
A simple command-line Java application designed to manage pet registration and schedule appointments (vet visits, grooming, etc.). The application ensures data persistence by automatically saving and loading all pet and appointment records to a local text file.

✨ Features
Pet Registration: Register new pets with unique IDs, species, age, and owner contact information.

Appointment Scheduling: Schedule various types of appointments (e.g., "vet visit," "grooming") with specific dates, times, and notes.

Data Persistence: Automatically saves and loads all data to/from pet_data.txt to maintain records across program runs.

Data Display: View all registered pets or all appointments for a specific pet.

Reporting: Generate reports, including pets with upcoming appointments and those overdue for a vet visit.

🚀 How to Run the Project
Prerequisites
You must have the Java Development Kit (JDK) installed on your system (Java 8 or newer is recommended).

Project Structure
This project relies on three main Java files within the Project2 package:

Project2/
├── PetCareSchedular.java  // Main application logic and menu
├── Pet.java               // Class to hold pet data and a list of appointments
└── Appointment.java       // Class to hold appointment details (type, date, time)
