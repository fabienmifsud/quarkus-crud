package com.fmi.quarkuscrud.resource;


import com.fmi.quarkuscrud.data.Booking;
import com.fmi.quarkuscrud.service.BookingService;
import com.fmi.quarkuscrud.service.ClientService;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@GraphQLApi
public class BookingResource {

    @Inject
    private BookingService bookingService;

    @Query("bookings")
    @Description("Get all Bookings")
    @RolesAllowed({"USER","ADMIN"})
    public List<Booking> findAll() {
        return this.bookingService.findAll();
    }

    @Query("bookingsByClient")
    @Description("Get all Bookings by Client")
    @RolesAllowed({"USER","ADMIN"})
    public List<Booking> findByClientIdOrderByBookingDateDesc(Long clientId) {
        return this.bookingService.findByClientIdOrderByBookingDateDesc(clientId);
    }

    @Mutation
    @Description("Create a new Booking")
    @RolesAllowed("ADMIN")
    public Boolean createBooking(Long clientId, Long productId) {
        return this.bookingService.createBooking(clientId, productId);
    }
}
