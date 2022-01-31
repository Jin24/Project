GNU nano 2.3.1                                                        
File: 02_script1.sql                                                                                                                        

SHOW DATABASES;
DROP DATABASE IF EXISTS 02_assign2db;
CREATE DATABASE 02_assign2db;
USE 02_assign2db; /*use the new database created*/



SHOW TABLES; /*should be none initially*/

CREATE TABLE bus(uniquePlateNum CHAR(7),capacity INT,PRIMARY KEY(uniquePlateNum));

CREATE TABLE passport(
        passportNum CHAR(4),
        citizenship CHAR(30),
        expiry DATE,
        birthDate DATE,
        PRIMARY KEY(passportNum));
CREATE TABLE bustrip(
	tripName CHAR(50),		
	tripID INT, 
	startDate DATE,
	endDate DATE,
	country CHAR(30),
        assignedPlate CHAR(7),
        PRIMARY KEY (tripID),
        FOREIGN KEY (assignedPlate) REFERENCES bus(uniquePlateNum));
CREATE TABLE passenger(
        passengerID INT,
        name CHAR(30),
        passengerPassport CHAR(4),
        PRIMARY KEY(passengerID),
        FOREIGN KEY (passengerPassport)
                REFERENCES passport(passportNum)
                ON DELETE CASCADE);
CREATE TABLE bookings(
        passengerID INT,
        tripID INT,
        tripCost INT,
        PRIMARY KEY(passengerID, tripID),
        FOREIGN KEY (passengerID) REFERENCES passenger(passengerID),
        FOREIGN KEY (tripID) REFERENCES bustrip(tripID));

SHOW TABLES;