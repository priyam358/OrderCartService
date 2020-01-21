package com.example.orderService.controller;


import com.example.orderService.dto.CartDetailsDTO;
import com.example.orderService.entity.CartDetails;
import com.example.orderService.service.CartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/addToCart") //naming convention
    public ResponseEntity<String> addCart(@RequestBody CartDetailsDTO cartDTO){

        CartDetails cartDetails  =new CartDetails();
        BeanUtils.copyProperties(cartDTO,cartDetails);

        CartDetails cartCreated=cartService.save(cartDetails);

        return new ResponseEntity<>(String.valueOf(cartCreated.getKeyId()), HttpStatus.CREATED);


    }

    @GetMapping("/getCart/{userId}")
    public List<CartDetails> getCart(@PathVariable("userId") String userId){
       return cartService.getCartDetails(userId);
    }

    @GetMapping(value ="/cartIncrement/{productId}/{userId}/{quantity}")
    public void cartIncrement(@PathVariable("productId") String productId,@PathVariable("userId") String userId,@PathVariable("quantity") Integer quantity){
             cartService.incrementCart(userId,productId,quantity);
    }








}
