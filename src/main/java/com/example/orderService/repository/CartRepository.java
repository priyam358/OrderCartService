package com.example.orderService.repository;

import com.example.orderService.entity.CartDetails;
import com.example.orderService.entity.OrderDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartRepository extends CrudRepository<CartDetails,String> {


    @Query(value = "Update cart_details set quantity =?1 where user_id =?2",nativeQuery=true)
    void updateQuantity(int quantity, String userId);


    @Query(value = "Select * from cart_details where user_id = ?1",nativeQuery = true)
    public List<CartDetails> getCartDetails(String userId);

    }


