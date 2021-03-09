package com.fmi.quarkuscrud.resource;


import com.fmi.quarkuscrud.data.Booking;
import com.fmi.quarkuscrud.service.BookingRepository;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.util.List;

@GraphQLApi
public class BookingResource {

    @Inject
    private BookingRepository bookingRepository;

    @Query("bookings")
    @Description("Get all Bookings")
    @RolesAllowed({"USER","ADMIN"})
    public List<Booking> findAll() {
        return this.bookingRepository.findAll().list();
    }

    @Query("bookingsByClient")
    @Description("Get all Bookings by Client")
    @RolesAllowed({"USER","ADMIN"})
    public List<Booking> findByClientIdOrderByBookingDateDesc(Long clientId) {
        return this.bookingRepository.findByClientIdOrderByBookingDateDesc(clientId);
    }

    @Mutation
    @Description("Create a new Booking")
    @RolesAllowed("ADMIN")
    public Boolean createBooking(Long clientId, Long productId) {
        return this.bookingRepository.createBooking(clientId, productId);
    }
}
