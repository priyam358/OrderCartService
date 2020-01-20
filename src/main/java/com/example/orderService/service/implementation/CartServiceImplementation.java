package com.example.orderService.service.implementation;

import com.example.orderService.entity.CartDetails;
import com.example.orderService.repository.CartRepository;
import com.example.orderService.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartServiceImplementation implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Override
    public CartDetails save(CartDetails cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void updateDetails(int quantity, String userId) {
        cartRepository.updateQuantity(quantity,userId);
    }

    @Override
    public List<CartDetails> getCartDetails(String userId) {
        return cartRepository.getCartDetails(userId);
    }


}
