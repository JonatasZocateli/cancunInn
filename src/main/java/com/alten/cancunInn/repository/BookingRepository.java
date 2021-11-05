package com.alten.cancunInn.repository;

import com.alten.cancunInn.repository.dao.BookingDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingDAO, Long> {

    @Query(
            value = "SELECT * FROM TB_BOOKING" +
                    " WHERE CHECK_IN_DATE  >= ?1", nativeQuery = true
    )
    List<BookingDAO> findBookingsFromDate(LocalDate startDate);
}
