package com.example.orderService.service.implementation;

import com.example.orderService.entity.CartDetails;
import com.example.orderService.repository.CartRepository;
import com.example.orderService.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.Null;
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
    @Transactional
    public void updateUserOnLogin(String guestUserId, String userId) {
        if(guestUserId.isEmpty() || userId.isEmpty()) return;

        cartRepository.updateUserOnLogin(guestUserId,userId);
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

    @Override
    @Transactional
    public void incrementCart(String userId,String productId,Integer quantity,String merchantId){

        cartRepository.incrementCart(userId,productId,quantity,merchantId);

    }
    /////CHECK ITS WORKINGGGGGG
    @Override
    public boolean checkIfProductIsPresent(String productId,String merchantId,String userId){
        return cartRepository.checkIfProductIsPresent(productId,merchantId,userId);
    }

    @Override
    @Transactional
    public void deleteCartRow(String userId, String merchantId, String productId) {
        cartRepository.deleteCartRow(userId,merchantId,productId);
    }
}
