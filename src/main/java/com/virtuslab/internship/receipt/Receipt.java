package com.virtuslab.internship.receipt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

public record Receipt(
        List<ReceiptEntry> entries,
        List<String> discounts,
        BigDecimal totalPrice) {

    public Receipt(List<ReceiptEntry> entries) {
        this(entries,
                new LinkedList<>(),
                entries.stream()
                        .map(ReceiptEntry::totalPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .setScale(2, RoundingMode.HALF_EVEN)
        );
    }
}
