package com.fmi.quarkuscrud.resource;


import com.fmi.quarkuscrud.data.Client;
import com.fmi.quarkuscrud.data.Product;
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
public class ClientResource {

    @Inject
    private ClientService clientService;

    @Query("clients")
    @Description("Get all Clients")
    @RolesAllowed({"USER","ADMIN"})
    public List<Client> findAll() {
        return this.clientService.findAll();
    }

    @Mutation
    @Description("Create a new Client")
    @RolesAllowed("ADMIN")
    public Boolean createClient(String name, String email, String dateOfBirth) {
        return this.clientService.createClient(name, email, dateOfBirth);
    }
}
