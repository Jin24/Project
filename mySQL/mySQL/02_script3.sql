USE 02_assign2db;

/* Query 1 */
SELECT * from bustrip WHERE country = 'Italy';

/* Query 2*/
SELECT DISTINCT name from passenger;

/* Query 3 */
SELECT * from bustrip ORDER BY tripName;

/* Query 4 */
Select tripName, country, startDate from bustrip WHERE startDate > '2022-04-22';

/* Query 5 */
SELECT name, birthDate FROM passenger INNER JOIN passport ON passenger.passportNum = passport.passportNum WHERE citizenship= 'USA';

/* Query 6 */
SELECT tripName, capacity from bustrip INNER JOIN bus on bustrip.uniquePlateNum = bus.uniquePlateNum WHERE startDate > '2022-04-01' AND startDate < '2022-09-01';


/* Query 7 */
SELECT * FROM passenger INNER JOIN passport ON passenger.passportID = passport.passportID WHERE expiry <= CURDATE() + INTERVAL 365 DAY;

/* Query 8 */
SELECT name, tripName FROM bustrip INNER JOIN passenger ON bustrip.passengerID = passenger.passengerID WHERE name = '&S&';

/* Query 9 */
SELECT COUNT(*) as capacity, name, tripID FROM passenger INNER JOIN books ON passenger.passengerID = bookings.passengerId INNER JOIN trip ON bookings.tripID = trip.tripID where name = 'Castles of Germany';

/* Query 11 */ 
SELECT firstName, lastName, homeCountry, name, country FROM passenger INNER JOIN books ON passenger.id = books.passengerId INNER JOIN trip ON books.tripId = trip.id INNER JOIN passport ON passport.id = passenger.passportId WHERE country != homeCountry;

/* Query 12 */
SELECT tripID, tripName from bustrip WHERE capacity != NULL;

/* Query 13 */
CREATE VIEW passengersPerTrip as SELECT tripID, COUNT(*) as 'passengerCount' FROM bus INNER JOIN trip ON bus.licensePlate = bustrip.busLicensePlate INNER JOIN books ON bookings.tripId = trip.id GROUP BY tripId;
SELECT name as 'Most Money Used', passengerCount as 'Number of time visited' FROM bus INNER JOIN trip ON bus.licensePlate = bustrip.busLicensePlate INNER JOIN passengersPerTrip ON passengersPerTrip.tripId = trip.id WHERE passengerCount > capacity;

/* Query 14 */
CREATE VIEW passengersPerTrip as SELECT tripID, COUNT(*) as 'passengerCount' FROM bus INNER JOIN trip ON bus.licensePlate = bustrip.busLicensePlate INNER JOIN books ON bookings.tripId = trip.id GROUP BY tripId;
SELECT tripName from bustrip WHERE passengerPerTrip < 4;

/* Query 15 */
CREATE VIEW passengersPerTrip as SELECT tripID, COUNT(*) as 'passengerCount' FROM bus INNER JOIN trip ON bus.licensePlate = bustrip.busLicensePlate INNER JOIN books ON bookings.tripId = trip.tripID GROUP BY tripID;
SELECT name as 'Bus Trip Name', passengerCount as 'Current Number of Passengers', capacity as 'Capacity of Assigned Bus', licensePlate as 'Current Bus Assigned License Plate' FROM bus INNER JOIN trip ON bus.uniqueBusPlate = trip.uniqueBusPlate INNER JOIN passengersPerTrip ON passengersPerTrip.tripID = trip.tripID WHERE passengerCount > capacity;