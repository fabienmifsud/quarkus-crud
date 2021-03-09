# Quarkus Crud with Postgres & GraphQL

This project is aimed to quickly test the stack :
- Quarkus (https://quarkus.io/)
- SmallRye GraphQL (https://quarkus.io/guides/microprofile-graphql)
- Hibernate Panache (https://quarkus.io/guides/hibernate-orm-panache)
- Database initialisation with Flyway (https://quarkus.io/guides/flyway)
- Quarkus Basic Auth (https://quarkus.io/guides/security-built-in-authentication#basic-auth)

## Clone & build

- Tests are executed on an h2 database
- Simply `mvn clean install` it

## Run it

You can run the app with a local postgres database by
- running `docker run --name some-postgres -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword -d postgres`
- `mvn quarkus:dev`
- Two users are available with Basic Auth :
  - standardUser (read only)
  - adminUser (create & read)

## GraphQl queries
```graphql
# Sample Queries available for users : standardUser, adminUser : 
query readClients {
  clients{
    id,
    name,
    email,
    dateOfBirth
  }
}

query readProducts {
  products{
    name,
    price
  }
}

query readBookings {
  bookings{
    product {
      name
    },
    client {
      name
    },
    bookingDate
  }
}

query readBookingsByClient {
  bookingsByClient (clientId: 1){
    product {
      name
    }
    bookingDate
  }
}

# Sample Mutations available for users : adminUser : 
mutation createClient {
    createClient(name: "toto3", email: "my.email@toto.com", dateOfBirth: "1986-01-17 00:12:12.000")
}

mutation createProduct {
    createProduct(name: "produit2", price: 10.45)
}

mutation createBooking {
    createBooking(productId: 2, clientId: 2)
}
```

Same app with other stacks : 
- https://github.com/fabienmifsud/spring-boot-crud