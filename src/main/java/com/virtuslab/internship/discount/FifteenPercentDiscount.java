package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class FifteenPercentDiscount {

    public static String NAME = "FifteenPercentDiscount";

    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = receipt
                    .totalPrice()
                    .multiply(BigDecimal.valueOf(0.85))
                    .setScale(2, RoundingMode.HALF_EVEN);
            var discounts = receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    private boolean shouldApply(Receipt receipt) {
        return receipt.entries()
                .stream()
                .filter(e -> e.product().type().equals(Product.Type.GRAINS))
                .mapToInt(ReceiptEntry::quantity).sum() >= 3;
    }
}
