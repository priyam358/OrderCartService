package com.example.orderService.service;

import com.example.orderService.entity.OrderDetails;
import org.hibernate.criterion.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderDetails save(OrderDetails orderDetails);

    List<OrderDetails>  fetchUserDetails(String merchantId,String productId);

}
