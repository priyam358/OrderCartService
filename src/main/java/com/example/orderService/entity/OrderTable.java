package com.example.orderService.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@Entity
@Table
public class OrderTable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int orderId;

    @CreationTimestamp
    private LocalDateTime time;

}
