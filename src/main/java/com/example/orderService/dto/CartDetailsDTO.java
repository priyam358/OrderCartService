package com.example.orderService.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailsDTO {

    private int userId;
    private int merchantId;
    private int productId;
    private int quantity;
    private double price;



}
