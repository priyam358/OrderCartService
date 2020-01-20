package com.example.orderService.service.implementation;

import com.example.orderService.entity.CartDetails;
import com.example.orderService.repository.CartRepository;
import com.example.orderService.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class CartServiceImplementation implements CartService {

    @Autowired
    private CartRepository cartRepository;

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

    @Override
    @Transactional
    public void emptyCart(String userId){
        cartRepository.deleteByUserId(userId);
       // List<CartDetails> cartDetails = cartRepository.removeByUserId("userId");

        //assertEquals("number of fruits are not matching", "2", cartDetails.size());
    }


}
