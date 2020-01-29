package com.example.orderService.service;

import com.example.orderService.entity.CartDetails;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartService  {

    CartDetails save(CartDetails cart);


    List<CartDetails> getCartDetails(String userId);

    void emptyCart(String userId);

    void incrementCart(String userId,String productId,Integer quantity,String merchantId);

    boolean checkIfProductIsPresent(String productId,String merchantId,String userId);

    void deleteCartRow(String userId,String merchantId,String productId);

    void updateUserOnLogin(String guestUserId, String userId);
}
