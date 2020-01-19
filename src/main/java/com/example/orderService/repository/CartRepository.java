package com.example.orderService.repository;

import com.example.orderService.entity.CartDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends CrudRepository<CartDetails,String> {

    @Override
    CartDetails save(CartDetails cart);


}
