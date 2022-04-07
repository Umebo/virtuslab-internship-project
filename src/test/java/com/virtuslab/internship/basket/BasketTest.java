package com.virtuslab.internship.basket;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BasketTest {

    @Test
    void shouldFillBasketWithGivenProductList() {
        // Given
        var productDb = new ProductDb();
        var products = new ArrayList<Product>();
        var cart = new Basket();
        var milk = productDb.getProduct("Milk");
        var bread = productDb.getProduct("Bread");
        var apple = productDb.getProduct("Apple");

        products.add(milk);
        products.add(bread);
        products.add(apple);

        // When
        cart.addMultipleProducts(products);

        // Then
        assertNotNull(cart);
        assertEquals(3, cart.getProducts().size());
    }

}
