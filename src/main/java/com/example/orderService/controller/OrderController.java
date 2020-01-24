package com.example.orderService.controller;


import com.example.orderService.dto.CartDetailsDTO;
import com.example.orderService.dto.OrderDetailsDTO;
import com.example.orderService.dto.OrderTableDTO;
import com.example.orderService.dto.StockCheckDTO;
import com.example.orderService.entity.CartDetails;
import com.example.orderService.entity.OrderDetails;
import com.example.orderService.entity.OrderTable;
import com.example.orderService.service.CartService;
import com.example.orderService.service.OrderService;
import com.example.orderService.service.implementation.CartServiceImplementation;
import com.example.orderService.service.implementation.OrderServiceImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
@CrossOrigin(origins =  "*", allowedHeaders = "*")
@EnableAsync
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;


    // hit from user view
  /*  @PostMapping("/save/{userId}") /// front end will send us userID
    public ResponseEntity<Integer> addOrderDetail(@PathVariable(name="userId") String userId){

       orderService.transferFromCart(userId);

    }*/
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
    public List<StockCheckDTO> sendEmail(@RequestBody List<CartDetailsDTO> orderDetailsDTOList, @PathVariable("userId") String userId) throws IOException, MessagingException {
        log.info("userID : {}, dto :{}",userId, orderDetailsDTOList);
        List<StockCheckDTO> stockCheckDTOList = orderService.checkStockAvailability(orderDetailsDTOList,userId);

        orderService.sendMail(userId);
        cartService.emptyCart(userId);
        return stockCheckDTOList;
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

    /*@PostMapping(value = "/setRating/{productId}/{merchantId}/{rating}")
    public void setRating(@PathVariable("productId") String productId,@PathVariable("merchantId") String merchantId,@PathVariable("rating") double rating){
            orderService.setRating(productId,merchantId,rating);

    }*/




    @PostMapping(value = "/setProductRating/{orderId}/{productId}/{merchantId}/{userId}/{rating}")
    public void setOrderRating(@PathVariable("orderId") int orderId,@PathVariable("productId") String productId,@PathVariable("merchantId") String merchantId,@PathVariable("userId") String userId,@PathVariable("rating") Double rating){
        orderService.setOrderRating(orderId,productId,merchantId,userId,rating);
        double productRating=orderService.productRating(productId);
        System.out.println(productRating);
        double merchantRating = orderService.merchantRating(merchantId);
        System.out.println(merchantRating);
        int countOfProduct = orderService.noOfProductsSold(productId);
        System.out.println(countOfProduct);
    }









}
