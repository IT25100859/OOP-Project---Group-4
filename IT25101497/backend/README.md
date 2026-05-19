# Component 04 - Movie Ticket Reservation and Seat Booking

## Project Overview
This is the backend component for the movie ticket reservation and seat booking system. It manages the core logic for seat availability, processes ticket bookings in real-time, and handles reservation tracking for the theater management system.

## Key Features Implemented
* **Real-time Seat Selection:** Tracks available and reserved seats to prevent double-booking issues.
* **Reservation Handling:** Manages ticket generation and maps seat allocations smoothly.
* **Data Persistence:** Uses file-based storage to read and write movie data and booking logs.
* **Input Validation:** Backend validation checks on incoming data before saving it to the files.

## Technologies Used
* **Language:** Java 17
* **Framework:** Spring Boot (Web Starter)
* **Tools:** Maven, Project Lombok, Embedded Tomcat Server

## Project Folder Structure & Architecture
* **Bean:** Holds the model/entity classes like `Seat` and `Booking`.
* **Controller:** Handles REST API endpoints for communication with the frontend or client.
* **Repository:** Responsible for file I/O operations (reading and writing data to files).
* **Service:** Contains the main business logic and reservation rules.
