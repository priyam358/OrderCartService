package com.example.orderService.repository;

import com.example.orderService.entity.CartDetails;
import com.example.orderService.entity.OrderDetails;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface CartRepository extends JpaRepository<CartDetails,String> {

  @Modifying
    @Query(value = "Update cart_details set quantity =?3 where user_id =?1 and product_id =?2",nativeQuery=true)
    void incrementCart(String userId,String productId,Integer quantity);


    @Query(value = "Select * from cart_details where user_id = ?1",nativeQuery = true)
    public List<CartDetails> getCartDetails(String userId);


    //@Modifying
    //@Query(value = "Delete from cart_details where user_id = ?1",nativeQuery = true)
    //public void deleteUserFromCart(String userId);

     void deleteByUserId(String userId);

    //@Query(value = "Select * from order_table where user_id = ?1",nativeQuery = true)
    //public List<CartDetails> getCartDetails(String userId);



    }


