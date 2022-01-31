USE 02_assign2db;
SELECT * FROM bus;
LOAD DATA LOCAL INFILE '/home/centos/assignments/assignment2/loaddatafall2021.txt'
INTO TABLE bus
FIELDS TERMINATED BY ',';
/*loading the load data fall file into the mysql workspace*/
SELECT * FROM bus;

SELECT * FROM passport;
INSERT INTO passport VALUES ('US10', 'USA', '2025-01-01', '1970-02-19');
INSERT INTO passport VALUES ('US12', 'USA', '2025-01-01', '1972-08-12');
INSERT INTO passport VALUES ('US13', 'USA', '2025-01-01', '2001-05-12');
INSERT INTO passport VALUES ('US14', 'USA', '2025-01-01', '2004-03-19');
INSERT INTO passport VALUES ('US15', 'USA', '2025-01-01', '2012-09-17');
INSERT INTO passport VALUES ('US22', 'USA', '2030-04-04', '1950-06-11');
INSERT INTO passport VALUES ('US23', 'USA', '2018-03-03', '1940-06-24');
INSERT INTO passport VALUES ('GE11', 'USA', '2027-01-01', '1970-07-12');
INSERT INTO passport VALUES ('US88', 'Canada', '2030-02-13', '1979-04-25');
INSERT INTO passport VALUES ('US89', 'Canada', '2022-02-02', '1976-04-08');
INSERT INTO passport VALUES ('US90', 'Italy', '2022-02-02', '1980-04-04');
INSERT INTO passport VALUES ('US91', 'Germany', '2030-01-01', '1959-02-02');
INSERT INTO passport VALUES ('US92', 'USA', '2030-01-01', '1980-05-02');
SELECT * FROM passport;



SELECT * FROM bustrip;
INSERT INTO bustrip VALUES ('Castles of Germany',1, '2022-01-01', '2022-01-17', 'Germany' , 'VAN1111');
INSERT INTO bustrip VALUES ('Tuscany Sunsets',2, '2022-03-03', '2022-03-14', 'Italy', 'TAXI222');
INSERT INTO bustrip VALUES ('California Wines',3, '2022-05-05', '2022-05-10', 'USA', 'VAN2222');
INSERT INTO bustrip VALUES ('Beaches Galore',4, '2022-04-04', '2022-04-14', 'Bermuda', 'TAXI222');
INSERT INTO bustrip VALUES ('Cottage Country',5, '2022-06-01', '2022-06-22', 'Canada', 'CAND123');
INSERT INTO bustrip VALUES ('Arrivaderci Roma',6, '2022-07-05', '2022-07-15', 'Italy', 'TAXI111');
INSERT INTO bustrip VALUES ('Shetland and Orkney',7, '2022-09-09', '2022-09-29', 'UK', 'TAXI111');
INSERT INTO bustrip VALUES ('Disney World and Sea World',8, '2022-06-10', '2022-06-20', 'USA', 'VAN2222');
INSERT INTO bustrip VALUES ('Crazy Monster Park',9, '2022-01-01', '2022-02-2', 'USA', 'VAN1111');
INSERT INTO bustrip VALUES ('Shoppers',10, '2022-02-12', '2022-02-15', 'Canada', 'CAND123');

SELECT * FROM bustrip;



SELECT * FROM passenger;
INSERT INTO passenger VALUES (1, 'Homer Simpson', 'US10');
INSERT INTO passenger VALUES (22, 'Marge Simpson', 'US12');
INSERT INTO passenger VALUES (33, 'Bart Simpson', 'US13');
INSERT INTO passenger VALUES (34, 'Lisa Simpson', 'US14');
INSERT INTO passenger VALUES (35, 'Maggie Simpson', 'US15');
INSERT INTO passenger VALUES (44, 'Ned Flanders', 'US22');
INSERT INTO passenger VALUES (45, 'Todd Flanders', 'US23');
INSERT INTO passenger VALUES (66, 'Heidi Klum', 'GE11');
INSERT INTO passenger VALUES (77, 'Michael Scott', 'US88');
INSERT INTO passenger VALUES (78, 'Dwight Schrute', 'US89');
INSERT INTO passenger VALUES (79, 'Pam Beesly', 'US90');
INSERT INTO passenger VALUES (80, 'Creed Bratton', 'US91');
INSERT INTO passenger VALUES (81, 'Heisenberg', 'US92');
SELECT * FROM passenger;



SELECT * FROM bookings;
INSERT INTO bookings VALUES(1, 1, 500); /* this is homer simpson*/
INSERT INTO bookings VALUES(22, 1, 500); /*This is marge simpson*/
INSERT INTO bookings VALUES(33, 1, 200); /*this is bart simpson*/
INSERT INTO bookings VALUES(34, 1, 200); /*this is lisa simpson*/
INSERT INTO bookings VALUES(35, 1, 200); /*This is maggie simposon*/
INSERT INTO bookings VALUES(66, 1, 600.99); /*Heidi Klum paid 600.99$ to go on castles of germany tour */
INSERT INTO bookings VALUES(44, 8, 400); /*Fladers trip*/
INSERT INTO bookings VALUES(45, 8, 200); /*Flander trip*/
INSERT INTO bookings VALUES(78, 4, 600); /*Dwight schrute*/
INSERT INTO bookings VALUES(80, 4, 600);  /*Creed*/
INSERT INTO bookings VALUES(78, 1, 550); /*Dwight doing another trip*/
INSERT INTO bookings VALUES(33, 8, 300); /*Bart and lisa doing disney trip*/
INSERT INTO bookings VALUES(34, 8, 300); /* ^ */
INSERT INTO bookings VALUES(1, 6, 600); /*all the simpsons doing a trip*/
INSERT INTO bookings VALUES(22, 6, 600); /* ^ */
INSERT INTO bookings VALUES(33, 6, 100); /* ^ */
INSERT INTO bookings VALUES(34, 6, 100); /* ^ */
INSERT INTO bookings VALUES(35, 6, 100); /* ^ */
INSERT INTO bookings VALUES(1, 7, 300); /* Homer doing a trip */
INSERT INTO bookings VALUES(44, 7, 400); /* ned flanders going on trip with homer */
INSERT INTO bookings VALUES(77, 7, 500); /* Michael Scott going on trip with homer and Flanders*/
INSERT INTO bookings VALUES(81, 10, 350); /* Heisenberg going on trip Shoppers */
SELECT * FROM bookings;


SELECT * FROM passport;
UPDATE passport SET citizenship = 'Germany' WHERE passportNum IN (SELECT passengerPassport FROM passenger WHERE name = 'Dwight Schrute');
SELECT passenger.passengerPassport, passenger.name, passport.citizenship FROM passenger INNER JOIN passport ON passenger.passengerPassport = passport.passportNum;

SELECT * FROM bustrip;
UPDATE bustrip SET assignedPlate = 'VAN1111' WHERE country = 'USA';

SELECT * FROM bustrip;