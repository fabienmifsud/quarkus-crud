package com.fmi.quarkuscrud.service;

import com.fmi.quarkuscrud.data.Booking;
import com.fmi.quarkuscrud.data.Client;
import com.fmi.quarkuscrud.data.Product;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class RepositoryTest {


    @Inject
    private ClientRepository clientRepository;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private BookingRepository bookingRepository;

    @Test
    void testTheWholeThing() throws ParseException {

        this.clientRepository.createClient("toto2", "my.email@toto.com", "1986-01-17 00:12:12.000");
        Client client = this.clientRepository.findById(1l);
        Assertions.assertEquals("my.email@toto.com", client.getEmail());
        Assertions.assertEquals("toto2", client.getName());

        List<Client> list = this.clientRepository.findAll().list();
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(client, list.get(0));

        this.productRepository.createProduct("product", 10.99d);
        Product product = this.productRepository.findById(1l);
        Assertions.assertEquals(10.99d, product.getPrice());
        Assertions.assertEquals("product", product.getName());

        List<Product> productList = this.productRepository.findAll().list();
        Assertions.assertEquals(1, productList.size());
        Assertions.assertEquals(product, productList.get(0));

        this.bookingRepository.createBooking(1l, 1l);
        Booking booking = this.bookingRepository.findById(1l);
        Assertions.assertEquals(1l, booking.getClient().getId());
        Assertions.assertEquals(1l, booking.getProduct().getId());

        List<Booking> bookingList = this.bookingRepository.findByClientIdOrderByBookingDateDesc(1l);
        Assertions.assertEquals(1, productList.size());
        Assertions.assertEquals(product, productList.get(0));

    }
}