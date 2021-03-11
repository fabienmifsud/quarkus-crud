package com.fmi.quarkuscrud.service;

import com.fmi.quarkuscrud.data.Booking;
import com.fmi.quarkuscrud.data.Client;
import com.fmi.quarkuscrud.data.Product;
import com.fmi.quarkuscrud.repository.BookingRepository;
import com.fmi.quarkuscrud.repository.ClientRepository;
import com.fmi.quarkuscrud.repository.ProductRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@QuarkusTest
public class ServiceTest {

    @InjectMock
    private ProductRepository productRepository;

    @InjectMock
    private ClientRepository clientRepository;

    @InjectMock
    private BookingRepository bookingRepository;

    @BeforeEach
    public void setup() throws ParseException {

        Product product1 = new Product();
        product1.setId(10l);
        product1.setPrice(10d);
        product1.setName("Ten");

        Product product2 = new Product();
        product2.setId(20l);
        product2.setPrice(20d);
        product2.setName("Twenty");

        PanacheQuery<Product> mock = Mockito.mock(PanacheQuery.class);
        when(mock.list()).thenReturn(Arrays.asList(product1, product2));

        when(productRepository.findAll()).thenReturn(mock);
        when(productRepository.createProduct("name", 10.0d)).thenReturn(true);

        Client client1 = new Client();
        client1.setId(10l);
        client1.setName("Pierre");
        client1.setEmail("pierre@gmail.com");
        client1.setDateOfBirth(new Date(15_000_000));

        Client client2 = new Client();
        client2.setId(20l);
        client2.setName("Paul");
        client2.setEmail("paul@gmail.com");
        client2.setDateOfBirth(new Date(5_000_000));

        PanacheQuery<Client> mock2 = Mockito.mock(PanacheQuery.class);
        when(mock2.list()).thenReturn(Arrays.asList(client1, client2));

        when(clientRepository.findAll()).thenReturn(mock2);
        when(clientRepository.createClient("Jacques", "jacques@gmail.com", "2021-03-21 00:00:00.000")).thenReturn(true);

        Booking booking1 = new Booking();
        booking1.setId(10l);
        booking1.setProduct(product1);
        booking1.setClient(client1);
        booking1.setBookingDate(new Date());

        Booking booking2 = new Booking();
        booking2.setId(20l);
        booking2.setProduct(product2);
        booking2.setClient(client2);
        booking1.setBookingDate(new Date());

        PanacheQuery<Booking> mock3 = Mockito.mock(PanacheQuery.class);
        when(mock3.list()).thenReturn(Arrays.asList(booking1, booking2));

        when(bookingRepository.findAll()).thenReturn(mock3);
        when(bookingRepository.findByClientIdOrderByBookingDateDesc(10l)).thenReturn(Arrays.asList(booking1));
        when(bookingRepository.createBooking(client1.getId(), product2.getId())).thenReturn(true);
    }

    @Test
    public void testProductQuery() {

        RestAssured.given()
                .when()
                .contentType("application/json")
                .body("{ \"query\": \"{" +
                        "  products {" +
                        "    name" +
                        "    price" +
                        "  }" +
                        "}\"" +
                        "}")
                .post("/graphql")
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("data.products.name", Matchers.hasItems("Ten", "Twenty"))
                .body("data.products.price", Matchers.hasItems(equalTo(10f), equalTo(20f)));
    }

    @Test
    public void testProductMutation() {

        RestAssured.given()
                .when()
                .contentType("application/json")
                .body("{ \"query\": \"mutation {createProduct(name: \\\"name\\\",price: 10.0)}\"}")
                .post("/graphql")
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("data.createProduct", equalTo(true));
    }

    @Test
    public void testClientQuery() {

        RestAssured.given()
                .when()
                .contentType("application/json")
                .body("{ \"query\": \"{" +
                        "  clients {" +
                        "    name" +
                        "    dateOfBirth" +
                        "  }" +
                        "}\"" +
                        "}")
                .post("/graphql")
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("data.clients.name", Matchers.hasItems("Pierre", "Paul"))
                .body("data.clients.dateOfBirth", Matchers.hasItems("1970-01-01T05:10:00", "1970-01-01T02:23:20"));
    }

    @Test
    public void testClientMutation() {

        RestAssured.given()
                .when()
                .contentType("application/json")
                .body("{ \"query\": \"mutation {createClient(name: \\\"Jacques\\\", email: \\\"jacques@gmail.com\\\", dateOfBirth: \\\"2021-03-21 00:00:00.000\\\")}\"}")
                .post("/graphql")
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("data.createClient", equalTo(true));
    }

    @Test
    public void testBookingQuery() {

        RestAssured.given()
                .when()
                .contentType("application/json")
                .body("{ \"query\": \"{" +
                        "  bookings {" +
                        "    client {" +
                        "       name" +
                        "    }" +
                        "    product {" +
                        "       name" +
                        "    }" +
                        "  }" +
                        "}\"" +
                        "}")
                .post("/graphql")
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("data.bookings.client.name", Matchers.hasItems("Pierre", "Paul"))
                .body("data.bookings.product.name", Matchers.hasItems("Ten", "Twenty"));
    }

    @Test
    public void testBookingByClientQuery() {

        RestAssured.given()
                .when()
                .contentType("application/json")
                .body("{ \"query\": \"{" +
                        "  bookingsByClient(clientId: 10) {" +
                        "    client {" +
                        "       name" +
                        "    }" +
                        "    product {" +
                        "       name" +
                        "    }" +
                        "  }" +
                        "}\"" +
                        "}")
                .post("/graphql")
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("data.bookingsByClient.client.name", Matchers.hasItems("Pierre"))
                .body("data.bookingsByClient.product.name", Matchers.hasItems("Ten"));
    }

    @Test
    public void testBookingMutation() {

        RestAssured.given()
                .when()
                .contentType("application/json")
                .body("{ \"query\": \"mutation {createBooking(clientId: 10, productId: 20)}\"}")
                .post("/graphql")
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("data.createBooking", equalTo(true));
    }

}
