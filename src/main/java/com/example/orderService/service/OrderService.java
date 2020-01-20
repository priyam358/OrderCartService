package com.example.orderService.service;

import com.example.orderService.entity.OrderDetails;
import com.example.orderService.entity.OrderTable;
import org.hibernate.criterion.Order;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface OrderService {



    OrderDetails saveDetail(OrderDetails orderDetails);


    OrderTable saveOrder(OrderTable orderTable);

    List<OrderDetails>  fetchUserDetails(String merchantId,String productId);

    void sendMail() throws MessagingException, IOException;

}
