package com.virtuslab.internship.controller;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/receipt")
public class RestReceiptController {

    @Resource
    private ReceiptService receiptService;

    @Resource
    private Basket basket;

    @GetMapping("/create")
    public Receipt CreateReceiptFromBasket() {
        return receiptService.applyDiscounts(basket);
    }

}
