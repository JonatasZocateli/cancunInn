package com.alten.cancunInn.repository.dao;


import com.alten.cancunInn.web.dto.BookingDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_BOOKING")
public class BookingDAO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idBooking;

    @Column(name = "checkInDate", nullable = false)
    private LocalDate checkInDate;

    @Column(name = "checkOutDate", nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "guestName", nullable = false)
    private String guestName;

}