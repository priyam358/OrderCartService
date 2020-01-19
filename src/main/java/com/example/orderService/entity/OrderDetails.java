package com.example.orderService.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name="orderDetails")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private int keyId;

    private String userId;
    private String productId;
    private String merchantId;
    private String orderId;
    int rating;
    Date timestamp;
    int quantity;
    double price;


}

