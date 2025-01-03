2.1 Access database 
This project will exercise how to add / retrieve / update records in database. Print the source code and the screen shot of a sample run.

Write a program that views, inserts, and updates book information stored in a database. You may use just command line to view/insert/update the information. No GUI needed.
When the user selects "view" option, the program displays a record with a specified ID. When the user selects "insert" option, the program inserts a new record. When the user selects "update" option, the program updates the record for the specified ID. 
The Book table is created as follows:create table Book (
id int(5) not null,
title varchar(100) not null,
pubDate date not null,
author char(50),
primary key (id)
); 

You can use any of your favorite database management systems, such as Microsoft Access, mysql, sqlite, etc.
You also need to create a database to put the previous Book table.
You MUST show the snapshot images of your dbms in your lab report.
 
2.2 Multi-Threading programming
Airline Reservation System
Simulate an airline reservation system with customer threads handling bookings.
Use wait/notify/notifyall to handle booking conflicts and seat availability.

Some hints:

The goal of this project is to learn thread synchronization concepts using wait() and notifyAll(). Students will model multiple threads accessing a shared resource (the flight) concurrently.

(1) Core Classes:

1) Flight - Represents the shared resource. Contains a number of seats and booked reservations.
2) Customer - Represents the threads. Try to book seats on the flight.

(2) Key Steps:

1) Create a Flight with limited seats (e.g. 20 seats)
2) Create multiple Customer threads (e.g. 25 customers)
3) Customers repeatedly try to book a seat on the Flight
3a. If seats available, book the reservation
3b. If no seats available, call wait() on Flight object to wait for seat
3c. Flight calls notifyAll() when a seat becomes available
4) A small number of customers who have reserved a seat successfully will randomly cancel the reservation and the seat becomes available again. You should consider a reasonable number of customers who may cancel their reservation so that the customers who are booking seats may not always succeed.

Add synchronization around seat booking logic to avoid race conditions

Observe how Customers are blocked on wait() when no seats, and notified when seats become free

 
2.3 Socket programming
 Weather Query System.

2.3.1 Overview:
Client-server project where client queries for weather data and server provides it.
Focuses on socket communication and data transfer between programs.

2.3.2 Server Design:
(1)	Opens TCP socket and listens for connections
(2)	Accepts connections and receives query city name from client
(3)	Looks up weather data for that city (can hardcode samples to start)
(4)	Sends weather data back to client as string
(5)	Can handle multiple sequential client connections

2.3.3 Client Design:
(1)	Opens socket and connects to server
(2)	Sends query city name entered by user
(3)	Receives weather data string from server
(4)	Prints out weather data for user

2.3.4 Example Usage:
Server starts and listens on port 5000
Client connects to server on port 5000
Client enters "Beijing" and sends it
Server receives "Beijing", looks up weather data
Server sends "Beijing: 31C and sunny"
Client prints "Beijing: 31C and sunny"
