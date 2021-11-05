package com.alten.cancunInn.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    private Long idBooking = null;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestName;

}
