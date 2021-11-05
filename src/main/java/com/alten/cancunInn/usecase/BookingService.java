package com.alten.cancunInn.usecase;

import com.alten.cancunInn.repository.dao.BookingDAO;
import com.alten.cancunInn.web.dto.BookingDTO;
import com.alten.cancunInn.web.dto.RoomAvailabiltyDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BookingService {

    public BookingDAO placeReservation(BookingDTO bookingDTO) throws Exception;

    public BookingDAO updateReservation(BookingDTO bookingDTO) throws Exception;

    public void cancelReservation(Long id);

    public List<RoomAvailabiltyDTO> listAvailability();
}
