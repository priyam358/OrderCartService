package com.example.orderService.service;

import com.example.orderService.entity.CartDetails;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartService  {

    CartDetails save(CartDetails cart);

    void updateDetails(int quantity,String userId);

    List<CartDetails> getCartDetails(String userId);

    void emptyCart(String userId);
}
