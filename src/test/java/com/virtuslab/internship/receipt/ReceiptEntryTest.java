package com.virtuslab.internship.receipt;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptEntryTest {

    @Test
    void shouldGenerateReceiptEntryForGivenProduct() {
        // Given
        var productDb = new ProductDb();
        var milk = productDb.getProduct("Milk");
        var quantity = 2;
        var expectedTotalPrice = milk.price()
                .multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_EVEN);

        // When
        var receiptEntry = new ReceiptEntry(milk,quantity);

        // Then
        assertNotNull(receiptEntry);
        assertEquals(2, receiptEntry.quantity());
        assertEquals(expectedTotalPrice, receiptEntry.totalPrice());
    }

    @Test
    void shouldNotGenerateReceiptEntryForQuantityLowerThan0() {
        // Given
        var productDb = new ProductDb();
        var milk = productDb.getProduct("Milk");
        var quantity = -1;

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            var receiptEntry = new ReceiptEntry(milk, quantity);
        });

        //Then
        assertEquals("Quantity or product value cannot be lower than 0!"
                , exception.getMessage());
    }

    @Test
    void shouldNotGenerateReceiptEntryForProductPriceLowerThan0() {
        // Given
        var beer = new Product("Beer", Product.Type.GRAINS, BigDecimal.valueOf(-0.5));
        var quantity = 3;

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            var receiptEntry = new ReceiptEntry(beer,quantity);
        });

        //Then
        assertEquals("Quantity or product value cannot be lower than 0!"
                , exception.getMessage());
    }

    @Test
    void shouldNotGenerateReceiptEntryForProductPriceAndQuantityLowerThan0() {
        // Given
        var beer = new Product("Beer", Product.Type.GRAINS, BigDecimal.valueOf(-3));
        var quantity = -2;

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            var receiptEntry = new ReceiptEntry(beer,quantity);
        });

        //Then
        assertEquals("Quantity or product value cannot be lower than 0!"
                , exception.getMessage());
    }
}
