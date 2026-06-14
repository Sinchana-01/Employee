# Employee Information Management System

## Overview

Employee Information Management System is a desktop-based Java application developed using Java Swing and Java IO Streams. The application reads employee information from a text file, processes the data, and displays it in a structured and interactive graphical user interface.

The system enables users to load employee records, search employees by ID, update employee information, delete employee records, and automatically save modifications back to the source file, ensuring data consistency and persistence.

## Key Features

* Load employee records from a text file
* Display employee information using JTable
* Search employees by Employee ID
* Update employee details
* Delete employee records
* Automatic synchronization of changes with the source file
* Duplicate Employee ID validation
* Progress bar for loading operations
* User-friendly Swing-based graphical interface
* Exception handling for reliable file operations

## Technologies Used

* Java
* Java Swing
* Java IO Streams

  * FileInputStream
  * InputStreamReader
  * BufferedReader
  * BufferedWriter
  * FileWriter
* Collections Framework (ArrayList)
* SwingWorker
* Eclipse IDE
* Git & GitHub

## Project Structure

```text
Employee-Information-Management-System
│
├── src
│   └── EmployeeManagementSystem.java
│
├── employees.txt
│
└── README.md
```

## Core Concepts Implemented

* Object-Oriented Programming (OOP)
* File Handling
* Java IO Streams
* Collections Framework
* Event Handling
* Exception Handling
* Multithreading using SwingWorker
* Desktop GUI Development with Swing

## Application Workflow

1. Employee records are stored in a text file.
2. The application reads employee data using Java IO Streams.
3. Employee information is parsed and stored as objects.
4. Records are displayed in a JTable.
5. Users can search, update, or delete employee records.
6. Any modifications are automatically saved back to the source file.
7. Updated information is available the next time the application is loaded.

## How to Run

1. Open the project in Eclipse IDE.
2. Ensure `employees.txt` is available in the project root directory.
3. Run `EmployeeManagementSystem.java`.
4. Click **Load Data** to load and display employee records.
5. Use the Search, Update, and Delete functionalities as required.



