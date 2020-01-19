package com.example.orderService.controller;


import com.example.orderService.dto.OrderDetailsDTO;
import com.example.orderService.entity.OrderDetails;
import com.example.orderService.service.OrderService;
import com.example.orderService.service.implementation.OrderServiceImplementation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/saveOrder")
    public ResponseEntity<String> addOrder(@RequestBody OrderDetailsDTO orderDetailsDTO){

        OrderDetails orderDetails=new OrderDetails();
        BeanUtils.copyProperties(orderDetailsDTO,orderDetails);    //first arg is source and second one is target

       OrderDetails orderCreated=orderService.save(orderDetails);

        return new ResponseEntity<String>(orderCreated.getOrderId(),HttpStatus.CREATED);
    }


    //@GetMapping("/customerDetails/")
    //public List<OrderDetails> fetchCustomerDetails(String merchantIdfromgateway,String productidfromapi)




}
