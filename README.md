# cancunInn

This API allows to manager room reservations.

# Availabitlity List
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
		"guestName": "Jonas" -> Guest name (In order to verifiy who reserve it)
	}


 # Update a reservation
## [PUT] `/reservations`
### [REQUEST]
	{
		"idBooking": 2, -> Id booking to update
		"checkInDate": "2021-11-06", -> Check-In date
		"checkOutDate": "2021-11-07", -> Check-Out date
		"guestName": "Jonas" -> Guest name (In order to verifiy who reserve it)
	}

# => Cancel a reservation
## [DELETE] `/reservations/{bookingId}` 

{bookingId} -> Booking ID to cancel

