package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

    @Autowired
    TenPercentDiscount tenPercentDiscount;

    @Autowired
    FifteenPercentDiscount fifteenPercentDiscount;

    public Receipt applyDiscounts(Basket basket) {
        Receipt receipt = ReceiptGenerator.generate(basket);
        receipt = fifteenPercentDiscount.apply(receipt);
        receipt = tenPercentDiscount.apply(receipt);
        return receipt;
    }
}
