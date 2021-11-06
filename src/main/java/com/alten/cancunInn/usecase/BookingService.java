package com.alten.cancunInn.usecase;

import com.alten.cancunInn.repository.dao.BookingDAO;
import com.alten.cancunInn.web.dto.BookingDTO;
import com.alten.cancunInn.web.dto.RoomAvailabilityDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BookingService {

    BookingDAO placeReservation(BookingDTO bookingDTO) throws Exception;

    BookingDAO updateReservation(BookingDTO bookingDTO, Long id) throws Exception;

    void cancelReservation(Long id);

    List<RoomAvailabilityDTO> listAvailability();
}
