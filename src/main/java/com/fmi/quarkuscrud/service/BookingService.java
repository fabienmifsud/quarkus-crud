package com.fmi.quarkuscrud.service;


import com.fmi.quarkuscrud.data.Booking;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class BookingService {

    public List<Booking> findAll() {
        return null;
    }

    public List<Booking> findByClientIdOrderByBookingDateDesc(Long clientId) {
        return null;
    }

    public Boolean createBooking(Long clientId, Long productId) {
        return null;
    }
}
