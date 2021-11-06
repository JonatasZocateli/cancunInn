# CANCUN INN

This API allows to manager room reservations.

# How to run it
## `./gradlew bootRun`

# Specifications
Language: Java 11
DB: H2


# Availability List
## [GET] `/reservations`

### [RESPONSE]
```json
{
    "date": "2021-11-07",
    "available": false,
    "guestName": "Jonas",
    "bookId": 1
}
```	
	
* Field `date` -> Date
* Field `available` -> True if the room is available or False if it is already reserved
* Field `guestName` -> Reservation guest's name (If room is reserved)
* Field `bookId` -> Reservation Id (If room is reserved)


    
# Place a room
## [POST] `/reservations`

### [REQUEST]
```json
{
    "checkInDate": "2021-11-06",
    "checkOutDate": "2021-11-07",
    "guestName": "Jonas"
}
```

* Field `checkInDate` -> Check-In date
* Field `checkOutDate` -> Check-Out date
* Field `guestName` -> Guest's name (In order to verifiy who reserved it)

 # Update a reservation
## [PUT] `/reservations/{bookingId}`

{bookingId} -> Booking ID to update

### [REQUEST]
```json
{
    "checkInDate": "2021-11-06", 
    "checkOutDate": "2021-11-07", 
    "guestName": "Jonas"
}
```

* Field `checkInDate` -> Check-In date
* Field `checkOutDate` -> Check-Out date
* Field `guestName` -> Guest's name (In order to verifiy who reserved it)

# Cancel a reservation
## [DELETE] `/reservations/{bookingId}` 

`bookingId` -> Booking ID to cancel

