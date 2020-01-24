package com.example.orderService.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailsDTO {


    private String userId;
    private String merchantId;
    private String productId;
    private int quantity;
    private double price;



}

