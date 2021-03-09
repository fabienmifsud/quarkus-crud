package com.fmi.quarkuscrud.service;


import com.fmi.quarkuscrud.data.Client;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.apache.commons.lang3.time.DateUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.text.ParseException;

@ApplicationScoped
public class ClientRepository implements PanacheRepository<Client>  {


    @Transactional
    public Boolean createClient(String name, String email, String dateOfBirth) throws ParseException {
        Client client = new Client();
        client.setEmail(email);
        client.setName(name);
        if(dateOfBirth != null) {
            client.setDateOfBirth(DateUtils.parseDate(dateOfBirth, "yyyy-MM-dd HH:mm:ss.SSS"));
        }

        this.persist(client);
        return true;
    }
}
