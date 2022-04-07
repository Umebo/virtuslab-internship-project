package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;

import java.util.*;

public class ReceiptGenerator {

    public static Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        Set<Product> productSet = new HashSet<>(basket.getProducts());
        for (Product product : productSet) {
            long quantity = basket
                    .getProducts()
                    .stream()
                    .filter(p -> p.name().equals(product.name()))
                    .count();
            receiptEntries.add(new ReceiptEntry(product, (int)quantity));
        }
        return new Receipt(receiptEntries);
    }
}
