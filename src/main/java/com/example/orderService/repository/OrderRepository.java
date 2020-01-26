package com.example.orderService.repository;

import com.example.orderService.entity.OrderDetails;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<OrderDetails,String> {


    List<OrderDetails> findByMerchantId(String merchantId);

    List<OrderDetails> findAllByUserId(String UserId);

    @Modifying
    @Query(value = "Update order_details set rating =?3 where product_id =?2 and merchant_id=?3",nativeQuery=true)
    void setRating(int rating, String productId,String merchantId);

    @Query(value="Select AVG(rating) from order_details where product_id=?1 and merchant_id=?2",nativeQuery = true)
    int computeAvg(String productId,String merchantId);

    List<OrderDetails>  findAllByOrderId(int orderId);


    @Modifying
    @Query(value = "update order_details set rating = ?1 where order_id =?2 and product_id =?3 and merchant_id =?4 and user_id =?5 ",nativeQuery = true)
    void updateRating(Double rating,int orderId,String productId,String merchantId,String userId);

    List<OrderDetails> findAllByMerchantId(String merchantId);

    List<OrderDetails> findAllByProductId(String productId);



}
