package com.alten.cancunInn.web;

import com.alten.cancunInn.repository.dao.BookingDAO;
import com.alten.cancunInn.usecase.BookingService;
import com.alten.cancunInn.web.dto.BookingDTO;
import com.alten.cancunInn.web.dto.RoomAvailabiltyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<RoomAvailabiltyDTO>> findAvailability(){
        return ResponseEntity.ok().body(bookingService.listAvailability());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookingDAO> bookRoom(@RequestBody BookingDTO bookingDTO) throws Exception {
        return ResponseEntity.ok().body(bookingService.placeReservation(bookingDTO));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookingDAO> updateReservation(@RequestBody BookingDTO bookingDTO) throws Exception {
        return ResponseEntity.ok().body(bookingService.updateReservation(bookingDTO));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> cancelReservation(@PathVariable ("id") long id){
        bookingService.cancelReservation(id);
        return ResponseEntity.ok().body("Book Deleted");
    }

}
