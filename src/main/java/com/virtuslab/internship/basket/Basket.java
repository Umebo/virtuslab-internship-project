package com.virtuslab.internship.basket;

import com.virtuslab.internship.product.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class Basket {

    private final List<Product> products;

    public Basket() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addMultipleProducts(List<Product> products) {
        for (Product p : products) {
            addProduct(p);
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void removeAllProducts() { products.clear(); }
}