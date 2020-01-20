package com.example.orderService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderDetailsDTO {

    private int keyId;
    private String userId;
    private String productId;
    private String merchantId;
    private int orderId;     //we will get this form the OrderTable
    double rating;
    int quantity;
    double price;


}
