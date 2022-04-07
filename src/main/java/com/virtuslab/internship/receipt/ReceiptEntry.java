package com.virtuslab.internship.receipt;

import com.virtuslab.internship.product.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record ReceiptEntry(
        Product product,
        int quantity,
        BigDecimal totalPrice) {

    public ReceiptEntry(Product product, int quantity) throws IllegalArgumentException{
        this(product, quantity, product.price()
                .multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_EVEN));
        if (product.price().compareTo(BigDecimal.ZERO) < 0 | quantity < 0)
            throw new IllegalArgumentException("Quantity or product value cannot be lower than 0!");
    }
}