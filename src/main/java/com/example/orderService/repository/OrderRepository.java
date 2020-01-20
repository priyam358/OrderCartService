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


    List<OrderDetails> findByMerchantIdAndProductId(String merchantId,String productId);

    List<OrderDetails> findAllByUserId(String UserId);

}
