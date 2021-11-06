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
	{
	    "date": "2021-11-07", -> Date
	    "available": false, -> If the room is available
	    "guestName": "Jonas", -> Reservation guest's name (If room is reserved)
	    "bookId": 1 -> Reservation Id (If room is reserved)
	}


    
 # Place a room
## [POST] `/reservations`

### [REQUEST]
	{
		"checkInDate": "2021-11-06", -> Check-In date
		"checkOutDate": "2021-11-07", -> Check-Out date
		"guestName": "Jonas" -> Guest name (In order to verifiy who reserved it)
	}


 # Update a reservation
## [PUT] `/reservations/{bookingId}`

{bookingId} -> Booking ID to update

### [REQUEST]
	{
		"checkInDate": "2021-11-06", -> Check-In date
		"checkOutDate": "2021-11-07", -> Check-Out date
		"guestName": "Jonas" -> Guest name (In order to verifiy who reserved it)
	}

# Cancel a reservation
## [DELETE] `/reservations/{bookingId}` 

{bookingId} -> Booking ID to cancel

