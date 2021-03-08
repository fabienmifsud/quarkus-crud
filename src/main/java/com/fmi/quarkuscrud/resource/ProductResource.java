package com.fmi.quarkuscrud.resource;


import com.fmi.quarkuscrud.data.Product;
import com.fmi.quarkuscrud.service.ProductService;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.util.List;

@GraphQLApi
public class ProductResource {

    @Inject
    private ProductService productService;

    @Query("products")
    @Description("Get all Products")
    @RolesAllowed({"USER","ADMIN"})
    public List<Product> findAll() {
        return this.productService.findAll();
    }

    @Mutation
    @Description("Create a new Product")
    @RolesAllowed("ADMIN")
    public Boolean createProduct(String name, Double price) {
        return this.productService.createProduct(name, price);
    }
}
