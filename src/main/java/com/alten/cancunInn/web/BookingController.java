package com.alten.cancunInn.web;

import com.alten.cancunInn.repository.dao.BookingDAO;
import com.alten.cancunInn.usecase.BookingService;
import com.alten.cancunInn.web.dto.BookingDTO;
import com.alten.cancunInn.web.dto.RoomAvailabilityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<RoomAvailabilityDTO>> findAvailability(){
        return ResponseEntity.ok().body(bookingService.listAvailability());
    }

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookingDAO> bookRoom(@RequestBody BookingDTO bookingDTO) throws Exception {
        return ResponseEntity.ok().body(bookingService.placeReservation(bookingDTO));
    }

    @PutMapping("/reservations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookingDAO> updateReservation(@PathVariable("id") Long id, @RequestBody BookingDTO bookingDTO) throws Exception {
        return ResponseEntity.ok().body(bookingService.updateReservation(bookingDTO, id));
    }

    @DeleteMapping(value = "/reservations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> cancelReservation(@PathVariable ("id") long id){
        bookingService.cancelReservation(id);
        return ResponseEntity.ok().body("Book Deleted");
    }

}
