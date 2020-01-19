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
    @SequenceGenerator(name="userId", initialValue=1, allocationSize=100)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="userId")
    private int userId;
    private int merchantId;
    private int productId;
    private int quantity;
    private double price;

}
