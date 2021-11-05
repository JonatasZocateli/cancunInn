package com.alten.cancunInn.usecase;

import com.alten.cancunInn.exceptions.PeriodValidateException;
import com.alten.cancunInn.repository.BookingRepository;
import com.alten.cancunInn.repository.dao.BookingDAO;
import com.alten.cancunInn.web.dto.BookingDTO;
import com.alten.cancunInn.web.dto.RoomAvailabiltyDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingServiceImplTest {

    @MockBean
    private BookingRepository bookingRepository;

    @Autowired
    private BookingRepository bookingRepositoryReal;

    @Autowired
    private BookingService bookingService;

    @Test
    void placeReservation() throws Exception {

        BookingDAO bookingDAO = Mockito.mock(BookingDAO.class);

        Mockito.when(bookingDAO.getCheckInDate()).thenReturn(null);
        Mockito.when(bookingDAO.getCheckInDate()).thenReturn(LocalDate.now().plusDays(1));
        Mockito.when(bookingDAO.getCheckOutDate()).thenReturn(LocalDate.now().plusDays(3));
        Mockito.when(bookingDAO.getGuestName()).thenReturn("James");
        Mockito.when(bookingRepository.save(ArgumentMatchers.eq(bookingDAO))).thenReturn(bookingDAO);

        BookingDTO bookingDTO = Mockito.mock(BookingDTO.class);

        Mockito.when(bookingDTO.getCheckInDate()).thenReturn(null);
        Mockito.when(bookingDTO.getCheckInDate()).thenReturn(LocalDate.now().plusDays(1));
        Mockito.when(bookingDTO.getCheckOutDate()).thenReturn(LocalDate.now().plusDays(3));
        Mockito.when(bookingDTO.getGuestName()).thenReturn("James");

        bookingService.placeReservation(bookingDTO);

        Mockito.verify(bookingRepository, Mockito.times(1)).save(ArgumentMatchers.any(BookingDAO.class));

    }

    @Test
    void cancelReservation() {
    }

    @Test
    @DisplayName("List room availability")
    void listAvailability() throws Exception {

        BookingDAO bookingDAO = Mockito.mock(BookingDAO.class);
        Mockito.when(bookingDAO.getIdBooking()).thenReturn(null);
        Mockito.when(bookingDAO.getCheckInDate()).thenReturn(LocalDate.now().plusDays(1));
        Mockito.when(bookingDAO.getCheckOutDate()).thenReturn(LocalDate.now().plusDays(3));
        Mockito.when(bookingDAO.getGuestName()).thenReturn("James");

        BookingDAO bookingDAO1 = new BookingDAO(1L, LocalDate.now().plusDays(1), LocalDate.now().plusDays(2), "James");
        List<BookingDAO> bookingDAOList = new ArrayList<>();
        bookingDAOList.add(bookingDAO1);

        Mockito.when(bookingRepositoryReal.findBookingsFromDate(ArgumentMatchers.any(LocalDate.class))).thenReturn(bookingDAOList);

        List<RoomAvailabiltyDTO> listAvailability = bookingService.listAvailability();
        System.out.println(listAvailability);
        Integer daysAvailable = listAvailability.stream().filter(it -> it.isAvailable()).collect(Collectors.toList()).size();
        Assertions.assertEquals(28, daysAvailable);

    }

    @Test
    @DisplayName("Trying to book a room today")
    void placeReservationToday() {
        BookingDTO bookingDAO = Mockito.mock(BookingDTO.class);
        Mockito.when(bookingDAO.getCheckInDate()).thenReturn(LocalDate.now());

        Assertions.assertThrows(
                PeriodValidateException.class,
                () -> bookingService.placeReservation(bookingDAO),
                "The stay must start at least tomorrow");

    }


    @Test
    @DisplayName("Trying to book a stay for more than 3 days")
    void placeOverReservation() {
        BookingDTO bookingDAO = Mockito.mock(BookingDTO.class);
        Mockito.when(bookingDAO.getCheckInDate()).thenReturn(LocalDate.now().plusDays(1));
        Mockito.when(bookingDAO.getCheckOutDate()).thenReturn(LocalDate.now().plusDays(5));
        Assertions.assertThrows(
                PeriodValidateException.class,
                () -> bookingService.placeReservation(bookingDAO),
                "The stay must not be longer than 3 days");

    }

    @Test
    @DisplayName("Trying to book a more than 30 days in advance")
    void placeReservationInAdvance() {
        BookingDTO bookingDAO = Mockito.mock(BookingDTO.class);
        Mockito.when(bookingDAO.getCheckInDate()).thenReturn(LocalDate.now().plusDays(31));
        Mockito.when(bookingDAO.getCheckOutDate()).thenReturn(LocalDate.now().plusDays(32));
        Assertions.assertThrows(
                PeriodValidateException.class,
                () -> bookingService.placeReservation(bookingDAO),
                "It is not allowed to book more than 30 days in advance");

    }


}