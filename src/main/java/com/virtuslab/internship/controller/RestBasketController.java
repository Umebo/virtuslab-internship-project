package com.virtuslab.internship.controller;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/basket")
public class RestBasketController {

    @Resource
    private Basket basket;

    @Resource
    private ProductDb productDb;

    @GetMapping("/get")
    public List<Product> ShowBasket() {
        return this.basket.getProducts();
    }

    @PostMapping("/add/{productName}")
    public void AddProductToBasket(@PathVariable String productName) {
        this.basket.addProduct(this.productDb.getProduct(productName));
    }

    @PostMapping("/add/list")
    public void addMultipleProductsToBasket(@RequestBody List<Product> productList) {
        this.basket.addMultipleProducts(productList);
    }

    @DeleteMapping("/clear")
    public void clearBasket() {
        this.basket.removeAllProducts();
    }

}
