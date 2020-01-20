package com.example.orderService.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name="CartDetails")
public class CartDetails {

    @Id
    @SequenceGenerator(name="keyId", initialValue=1, allocationSize=100)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="keyId")
    private int keyId;
    private String userId;
    private String merchantId;
    private String productId;
    private int quantity;
    private double price;
///
}
