package com.example.orderService.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockCheckDTO {
    String userId;
    String productId;
    String merchantId;
    int quantity;
    boolean status;

}
