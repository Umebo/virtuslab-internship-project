package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FifteenPercentDiscountTest {

    @Test
    void shouldApply15PercentDiscountWhenMoreThan3Grains() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        var butter = productDb.getProduct("Butter");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 4));
        receiptEntries.add(new ReceiptEntry(cereals, 1));
        receiptEntries.add(new ReceiptEntry(butter, 1));

        var receipt = new Receipt(receiptEntries);
        var discount = new FifteenPercentDiscount();
        var expectedTotalPrice = bread.price()
                .multiply(BigDecimal.valueOf(4))
                .add(cereals.price())
                .add(butter.price())
                .multiply(BigDecimal.valueOf(0.85))
                .setScale(2, RoundingMode.HALF_EVEN);

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldApply15PercentDiscountWhenExactly3Grains() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 2));
        receiptEntries.add(new ReceiptEntry(cereals, 1));

        var receipt = new Receipt(receiptEntries);
        var discount = new FifteenPercentDiscount();
        var expectedTotalPrice = bread.price()
                .multiply(BigDecimal.valueOf(2))
                .add(cereals.price())
                .multiply(BigDecimal.valueOf(0.85))
                .setScale(2, RoundingMode.HALF_EVEN);

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldNotApply15PercentDiscountWhenNoGrains() {
        // Given
        var productDb = new ProductDb();
        var tomato = productDb.getProduct("Tomato");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(tomato, 2));

        var receipt = new Receipt(receiptEntries);
        var discount = new TenPercentDiscount();
        var expectedTotalPrice = tomato.price()
                .multiply(BigDecimal.valueOf(2))
                .setScale(2, RoundingMode.HALF_EVEN);

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldNotApply15PercentDiscountWhenLessThan3Grains() {
        // Given
        var productDb = new ProductDb();
        var tomato = productDb.getProduct("Tomato");
        var bread = productDb.getProduct("Bread");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(tomato, 2));
        receiptEntries.add(new ReceiptEntry(bread, 1));

        var receipt = new Receipt(receiptEntries);
        var discount = new TenPercentDiscount();
        var expectedTotalPrice = tomato.price()
                .multiply(BigDecimal.valueOf(2))
                .add(bread.price())
                .setScale(2, RoundingMode.HALF_EVEN);

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }
}
