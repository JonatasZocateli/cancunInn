package com.alten.cancunInn.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RoomAvailabilityDTO {
    private LocalDate date;
    private boolean available;
    private String guestName;
    private Long bookId;
}
