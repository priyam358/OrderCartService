package com.example.orderService.service;

import com.example.orderService.dto.CartDetailsDTO;
import com.example.orderService.dto.OrderDetailsDTO;
import com.example.orderService.dto.StockCheckDTO;
import com.example.orderService.entity.OrderDetails;
import com.example.orderService.entity.OrderTable;
import freemarker.template.TemplateException;
import org.hibernate.criterion.Order;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface OrderService {



    OrderDetails saveDetail(OrderDetails orderDetails);


    OrderTable saveOrder(OrderTable orderTable);

    List<OrderDetails>  fetchUserDetails(String merchantId,String productId);

    void sendMail(String userId) throws MessagingException, IOException, TemplateException;

    List<OrderTable> getOrderLog();

    List<OrderDetails> getRecentOrders(String userId);

    void setRating(String productId,String merchantId,int rating);

    List<StockCheckDTO> checkStockAvailability(List<CartDetailsDTO> orderDetailsDTOList, String userId);

    OrderDetails transferFromCart(String userId);

    double merchantRating(String merchantId);

    double productRating(String productId);

    void setOrderRating(int orderId,String productId,String merchantId,String userId,Double rating);

    int noOfProductsSold(String productId);


}
