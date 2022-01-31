USE 02_assign2db;

CREATE VIEW viewTrip as SELECT name, tripCost FROM bookings INNER JOIN passenger ON bookings.passengerID = passenger.passengerID INNER JOIN bustrip ON bustrip.tripID = bookings.tripID;

SELECT * from viewTrip WHERE tripName LIKE '%Castles%';

SELECT * from bus;

DELETE FROM bus WHERE uniquePlateNum LIKE '%UWO%';

SELECT * from bus;

SELECT * from passenger;

DELETE FROM passport WHERE citizenship = 'Canada';

SELECT * from passport;

SELECT * from passenger;
/* The passports where the passengers' home country was 'Canada' was deleted.*/


SELECT * from bustrip;

DELETE FROM bustrip WHERE tripName = 'California Wines';


SELECT * from bustrip;

/* The trips where the names were 'California Wines' were deleted due to dependancy */
DELETE FROM bustrip WHERE tripName = 'Arrivaderci Roma';
/* This could not be deleted because there are no trips that are booked with this destination that were not deleted from previous deletion commands */

SELECT * from passenger;

DELETE FROM passenger WHERE name= 'Pam Beesly';

SELECT * from passenger;

SELECT * from viewTrip;

DELETE FROM passenger WHERE name LIKE '&Simpson&';

SELECT * from viewTrip;