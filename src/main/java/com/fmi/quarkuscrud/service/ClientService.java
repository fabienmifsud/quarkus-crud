package com.fmi.quarkuscrud.service;


import com.fmi.quarkuscrud.data.Client;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ClientService {

    public List<Client> findAll() {
        return null;
    }

    public Boolean createClient(String name, String email, String dateOfBirth) {
        return null;
    }
}
