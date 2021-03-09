package com.fmi.quarkuscrud.service;


import com.fmi.quarkuscrud.data.Booking;
import com.fmi.quarkuscrud.data.Client;
import com.fmi.quarkuscrud.data.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class BookingRepository implements PanacheRepository<Booking> {

    public List<Booking> findByClientIdOrderByBookingDateDesc(Long clientId) {
        return find("client.id", Sort.by("bookingDate").descending(), clientId).list();
    }

    @Transactional
    public Boolean createBooking(Long clientId, Long productId) {

        Booking booking = new Booking();
        booking.setBookingDate(new Date());
        Client client = new Client();
        client.setId(clientId);
        booking.setClient(client);
        Product product = new Product();
        product.setId(productId);
        booking.setProduct(product);

        this.persist(booking);

        return true;
    }
}
