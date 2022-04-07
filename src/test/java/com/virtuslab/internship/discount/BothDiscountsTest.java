package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BothDiscountsTest {

    @Test
    void shouldApplyBothDiscountsWhenMoreThan50AfterFirst() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        var steak = productDb.getProduct("Steak");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 2));
        receiptEntries.add(new ReceiptEntry(cereals, 1));
        receiptEntries.add(new ReceiptEntry(steak, 1));

        var receipt = new Receipt(receiptEntries);
        var tenPercentDiscount = new TenPercentDiscount();
        var fifteenPercentDiscount = new FifteenPercentDiscount();
        var expectedTotalPrice = bread.price()
                .multiply(BigDecimal.valueOf(2))
                .add(cereals.price())
                .add(steak.price())
                .multiply(BigDecimal.valueOf(0.85))
                .multiply(BigDecimal.valueOf(0.9))
                .setScale(2, RoundingMode.HALF_EVEN);

        // When
        Receipt receiptAfterDiscounts;
        receiptAfterDiscounts = fifteenPercentDiscount.apply(receipt);
        receiptAfterDiscounts = tenPercentDiscount.apply(receiptAfterDiscounts);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscounts.totalPrice());
        assertEquals(2, receiptAfterDiscounts.discounts().size());
    }

    @Test
    void shouldApplyBothDiscountsWhenExactly50AfterFirst() {
        // Given
        var productDb = new ProductDb();
        var beer = new Product("Beer", Product.Type.GRAINS, BigDecimal.valueOf(8.82));
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(beer, 1));
        receiptEntries.add(new ReceiptEntry(bread, 2));
        receiptEntries.add(new ReceiptEntry(cereals, 5));

        var receipt = new Receipt(receiptEntries);
        var tenPercentDiscount = new TenPercentDiscount();
        var fifteenPercentDiscount = new FifteenPercentDiscount();
        var expectedTotalPrice = beer.price()
                .add((bread.price()).multiply(BigDecimal.valueOf(2)))
                .add((cereals.price()).multiply(BigDecimal.valueOf(5)))
                .multiply(BigDecimal.valueOf(0.85))
                .multiply(BigDecimal.valueOf(0.9))
                .setScale(2, RoundingMode.HALF_EVEN);

        // When
        Receipt receiptAfterDiscounts;
        receiptAfterDiscounts = fifteenPercentDiscount.apply(receipt);
        receiptAfterDiscounts = tenPercentDiscount.apply(receiptAfterDiscounts);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscounts.totalPrice());
        assertEquals(2, receiptAfterDiscounts.discounts().size());
    }

    @Test
    void shouldNotApplyBothDiscountsWhenLessThan50AfterFirst() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        var pork = productDb.getProduct("Pork");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 2));
        receiptEntries.add(new ReceiptEntry(pork, 2));

        var receipt = new Receipt(receiptEntries);
        var tenPercentDiscount = new TenPercentDiscount();
        var fifteenPercentDiscount = new FifteenPercentDiscount();
        var expectedTotalPrice = bread.price()
                .add(cereals.price().multiply(BigDecimal.valueOf(2)))
                .add(pork.price().multiply(BigDecimal.valueOf(2)))
                .multiply(BigDecimal.valueOf(0.85))
                .setScale(2, RoundingMode.HALF_EVEN);

        // When
        Receipt receiptAfterDiscounts;
        receiptAfterDiscounts = fifteenPercentDiscount.apply(receipt);
        receiptAfterDiscounts = tenPercentDiscount.apply(receiptAfterDiscounts);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscounts.totalPrice());
        assertEquals(1, receiptAfterDiscounts.discounts().size());
    }
}
