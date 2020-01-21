package com.example.orderService.controller;


import com.example.orderService.dto.OrderDetailsDTO;
import com.example.orderService.dto.OrderTableDTO;
import com.example.orderService.entity.CartDetails;
import com.example.orderService.entity.OrderDetails;
import com.example.orderService.entity.OrderTable;
import com.example.orderService.service.CartService;
import com.example.orderService.service.OrderService;
import com.example.orderService.service.implementation.CartServiceImplementation;
import com.example.orderService.service.implementation.OrderServiceImplementation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;



    // hit from user view
    @PostMapping("/save/{userId}") /// front end will send us userID
    public ResponseEntity<Integer> addOrderDetail(@PathVariable(name="userId") String userId){

        OrderTable orderTable =new OrderTable();
        OrderTableDTO orderTableDTO = new OrderTableDTO();
        BeanUtils.copyProperties(orderTableDTO,orderTable);    //first arg is source and second one is target
        OrderTable orderTableCreated=orderService.saveOrder(orderTable);
        int orderId=orderTableCreated.getOrderId();


         List<CartDetails> details=cartService.getCartDetails(userId);
         OrderDetails orderDetails=new OrderDetails();

         orderDetails.setOrderId(orderId);
         orderDetails.setMerchantId(details.get(0).getMerchantId());
         orderDetails.setPrice(details.get(0).getPrice());
         orderDetails.setUserId(userId);
         orderDetails.setProductId(details.get(0).getProductId());
         orderDetails.setQuantity(details.get(0).getQuantity());

        OrderDetails orderCreated=orderService.saveDetail(orderDetails);

        return new ResponseEntity<Integer>(orderCreated.getKeyId(),HttpStatus.CREATED);

    }
     /// hit from merchant view
    @GetMapping(value="/customerDetails/{merchantId}/{productId}")
    public List<OrderDetails> fetchCustomerDetails(@PathVariable("merchantId") String merchantId,@PathVariable("productId") String productId){
        return orderService.fetchUserDetails(merchantId,productId);

    }
    /// on click of checkout in user view
    //// to do in this is send email to the user as well
    ///empty cart
    /// to do CHANGE PATH VARIABLE TO REQUEST PARAMS
    //SHOULD BE A DIFFERENT MICROSERVICE
    //
    @PostMapping(value="/checkout/{userId}")
    public String sendEmail(@PathVariable("userId") String userId) throws IOException, MessagingException {
       // orderService.sendMail();
        cartService.emptyCart(userId);

        return "Email sent successfully";
    }

    @GetMapping(value ="/orderLog")
    public List<OrderTable> getOrderLog(){
       return orderService.getOrderLog();
    }
    //User will hit this
    @GetMapping(value="/getRecentOrders/{userId}")
    public List<OrderDetails> getRecentOrders(@PathVariable("userId") String userId){
        return orderService.getRecentOrders(userId);
    }









}
