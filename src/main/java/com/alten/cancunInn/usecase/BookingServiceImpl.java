package com.alten.cancunInn.usecase;

import com.alten.cancunInn.exceptions.PeriodValidateException;
import com.alten.cancunInn.repository.BookingRepository;
import com.alten.cancunInn.repository.dao.BookingDAO;
import com.alten.cancunInn.web.dto.BookingDTO;
import com.alten.cancunInn.web.dto.RoomAvailabiltyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public BookingDAO placeReservation(BookingDTO bookingDTO) throws Exception {

        checkStartLimit(bookingDTO);
        checkDailyLimit(bookingDTO);
        checkPeriodOfStay(bookingDTO);
        checkIfPeriodIsUnavailable(bookingDTO);

        return bookingRepository.save(
                new BookingDAO(bookingDTO.getIdBooking(),
                        bookingDTO.getCheckInDate(),
                        bookingDTO.getCheckOutDate(),
                        bookingDTO.getGuestName()));
    }

    @Override
    public void cancelReservation(Long id) {
        try {
            bookingRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EmptyResultDataAccessException(ex.getMessage(), ex.getExpectedSize());
        }
    }

    @Override
    public List<RoomAvailabiltyDTO> listAvailability() {
        List<RoomAvailabiltyDTO> periodList = new ArrayList();
        for (int day = 1; day <= 30; day++) {
            periodList.add(verifyIfDateIsAvailable(LocalDate.now().plusDays(day)));
        }

        return periodList;
    }

    private void checkStartLimit(BookingDTO bookingDTO) throws Exception {
        if (bookingDTO.getCheckInDate().isBefore(LocalDate.now().plusDays(1))) {
            throw new PeriodValidateException("The stay must start at least tomorrow");
        }
    }

    private void checkDailyLimit(BookingDTO bookingDTO) throws Exception {
        if (DAYS.between(bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate()) > 2) {
            throw new PeriodValidateException("The stay must not be longer than 3 days");
        }
    }


    private void checkPeriodOfStay(BookingDTO bookingDTO) throws Exception {

        if (bookingDTO.getCheckInDate().isAfter(bookingDTO.getCheckOutDate())) {
            throw new PeriodValidateException("Check-In date must be after than check-out date");
        }

        if (bookingDTO.getCheckOutDate().isAfter(LocalDate.now().plusDays(30))) {
            throw new PeriodValidateException("It is not allowed to book more than 30 days in advance");
        }
    }

    private RoomAvailabiltyDTO verifyIfDateIsAvailable(LocalDate dateToValidate) {

        for (BookingDAO unavailablePeriod : bookingRepository.findBookingsFromDate(LocalDate.now().plusDays(1))) {
            if (dateToValidate.isAfter(unavailablePeriod.getCheckInDate())
                    && dateToValidate.isBefore(unavailablePeriod.getCheckOutDate())) {
                return new RoomAvailabiltyDTO(dateToValidate, false, unavailablePeriod.getGuestName(), unavailablePeriod.getIdBooking());
            }

            if (dateToValidate.isEqual(unavailablePeriod.getCheckInDate())
                    || dateToValidate.isEqual(unavailablePeriod.getCheckOutDate())) {
                return new RoomAvailabiltyDTO(dateToValidate, false, unavailablePeriod.getGuestName(), unavailablePeriod.getIdBooking());
            }
        }

        return new RoomAvailabiltyDTO(dateToValidate, true, null, null);
    }


    private void checkIfPeriodIsUnavailable(BookingDTO bookingDTO) throws Exception {
        LocalDate startDate = bookingDTO.getCheckInDate();
        while (!startDate.isAfter(bookingDTO.getCheckOutDate())) {
            RoomAvailabiltyDTO roomAvailabiltyDTO = verifyIfDateIsAvailable(startDate);
            if (!roomAvailabiltyDTO.isAvailable()) {
                if (!roomAvailabiltyDTO.getBookId().equals(bookingDTO.getIdBooking())) {
                    throw new PeriodValidateException("The selected period is in concurrency with other reservation. Check availability again");
                }
            }
            startDate = startDate.plusDays(1);
        }
    }

}
