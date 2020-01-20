package com.example.orderService.repository;

import com.example.orderService.entity.OrderTable;
import org.springframework.data.repository.CrudRepository;

public interface OrderTableRepository extends CrudRepository<OrderTable,String> {
}
