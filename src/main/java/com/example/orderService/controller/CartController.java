package com.example.orderService.controller;


import com.example.orderService.dto.CartDetailsDTO;
import com.example.orderService.entity.CartDetails;
import com.example.orderService.service.CartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/saveOrder")
    public ResponseEntity<String> addCart(@RequestBody CartDetailsDTO cartDTO){

        CartDetails cartDetails  =new CartDetails();
        BeanUtils.copyProperties(cartDTO,cartDetails);    //first arg is source and second one is target

        CartDetails cartCreated=cartService.save(cartDetails);

        return new ResponseEntity<String>(String.valueOf(cartCreated.getUserId()),HttpStatus.CREATED);


    }


}
