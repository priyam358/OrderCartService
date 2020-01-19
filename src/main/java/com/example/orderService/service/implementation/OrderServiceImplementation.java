package com.example.orderService.service.implementation;

import com.example.orderService.entity.OrderDetails;
import com.example.orderService.repository.OrderRepository;
import com.example.orderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImplementation implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderDetails save(OrderDetails orderDetails) {
        return orderRepository.save(orderDetails);
    }

    @Override
    public List<OrderDetails> fetchUserDetails(String merchantId, String productId) {
        return orderRepository.userDetails(merchantId,productId);
    }


}
