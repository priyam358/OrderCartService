package com.example.orderService.repository;

import com.example.orderService.entity.OrderDetails;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<OrderDetails,String> {


    @Query(value = "Select * from order_details where merchant_id=?1 and product_id=?2",nativeQuery=true)
    List<OrderDetails> userDetails(String merchantId,String productId);

}
