package com.fmi.quarkuscrud.resource;


import com.fmi.quarkuscrud.data.Client;
import com.fmi.quarkuscrud.repository.ClientRepository;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.text.ParseException;
import java.util.List;

@GraphQLApi
public class ClientResource {

    @Inject
    private ClientRepository clientRepository;

    @Query("clients")
    @Description("Get all Clients")
    @RolesAllowed({"USER", "ADMIN"})
    public List<Client> findAll() {
        return this.clientRepository.findAll().list();
    }

    @Mutation
    @Description("Create a new Client")
    @RolesAllowed("ADMIN")
    public Boolean createClient(String name, String email, String dateOfBirth) throws ParseException {
        return this.clientRepository.createClient(name, email, dateOfBirth);
    }
}
