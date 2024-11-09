# Electricity-Billing-System

The Electricity Billing System is a Java application that allows users to manage and calculate electricity bills. It provides an interactive GUI for entering customer details, calculating bill amounts based on units consumed, saving the bill details to a MySQL database, and viewing individual or all stored bills.

## Features
Calculate Bill: Computes the bill amount based on units consumed at a rate of ₹1.5 per unit.
Save Bill: Stores customer information, meter number, units consumed, and bill amount in a MySQL database.
Display Bill: Fetches and displays bill details for a specified meter number.
View All Bills: Shows a list of all stored bills.

## Technologies Used
Java: Core language for application logic and GUI (Swing).
MySQL: Database for storing bill records.
Swing: GUI framework for creating an interactive interface.

## Database Setup
1. Open MySQL and create a new database with the following commands given in "DATABASE ElectricityBillingSystem folder"
2. Update the database connection details in the ElectricityBillingSystem class if they differ from the default (DB_USER: root, DB_PASS: root)

## Installation and Usage
Clone the repository or download the code.
Open the project in an IDE that supports Java, such as IntelliJ IDEA or Eclipse.
Compile and run the program.

Use the application interface to:
Enter customer details and units consumed.
Calculate and save bills.
View individual or all saved bills.

## Code Overview
calculateBill: Computes bill amount based on units consumed.
saveBill: Saves bill details to the database.
displayBill: Fetches and displays a bill based on a given meter number.
viewAllBills: Displays all stored bills.

## Prerequisites
Java Development Kit (JDK) installed.
MySQL server installed and running.

## Future Enhancements
Potential improvements include:

Adding customer login functionality.
Including more detailed billing rates or a tiered pricing model.
Enhancing error handling and input validation.
