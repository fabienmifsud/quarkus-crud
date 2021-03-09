package com.fmi.quarkuscrud.service;


import com.fmi.quarkuscrud.data.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    @Transactional
    public Boolean createProduct(String name, Double price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);

        this.persist(product);
        return true;
    }
}
