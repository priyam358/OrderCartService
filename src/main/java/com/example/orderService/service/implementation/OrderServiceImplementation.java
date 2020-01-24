package com.example.orderService.service.implementation;

import com.example.orderService.dto.CartDetailsDTO;
import com.example.orderService.dto.OrderDetailsDTO;
import com.example.orderService.dto.OrderTableDTO;
import com.example.orderService.dto.StockCheckDTO;
import com.example.orderService.entity.CartDetails;
import com.example.orderService.entity.OrderDetails;
import com.example.orderService.entity.OrderTable;
import com.example.orderService.repository.OrderRepository;
import com.example.orderService.repository.OrderTableRepository;
import com.example.orderService.service.CartService;
import com.example.orderService.service.MerchantFeign;
import com.example.orderService.service.OrderService;
import com.example.orderService.service.ProductFeign;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sun.jvm.hotspot.debugger.AddressException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class OrderServiceImplementation implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderTableRepository orderTableRepository;

    @Autowired
    MerchantFeign merchantFeign;

    @Autowired
    CartService cartService;

    @Autowired
    ProductFeign productFeign;

    @Override
    public OrderDetails saveDetail(OrderDetails orderDetails) {
        return orderRepository.save(orderDetails);

    }

    @Override
    public OrderTable saveOrder(OrderTable orderTable) {
        return orderTableRepository.save(orderTable);
    }

    @Override
    public List<OrderDetails> fetchUserDetails(String merchantId, String productId) {
        return orderRepository.findByMerchantIdAndProductId(merchantId,productId);
    }

    @Override
    @Async
    public void sendMail(String userId) throws AddressException, MessagingException, IOException {
        List<OrderDetails> orderId=orderRepository.findAllByUserId(userId);
        int recentOrderId=orderId.get(orderId.size()-1).getOrderId();
        List<OrderDetails> orders=orderRepository.findAllByOrderId(recentOrderId);
        //OrderDetails orderDetails=orderRepository.findByUserIdandOrderId(String userId,int orderId);
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("priyam.shah@coviam.com", "indxmatyliovrupr");
            }
        });
        for (OrderDetails orderDetails: orders
                ) {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("priyam.shah@coviam.com", false));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("priyamshah358@gmail.com"));
            msg.setSubject("Your Order Summary :");
            msg.setContent("Your Order Summary :", "text/html");
            msg.setSentDate(new Date());
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            String emailMessage;
            emailMessage=orderDetails.getUserId()+"\n"+orderDetails.getOrderId()+"\n"+orderDetails.getProductId()+"\n"+orderDetails.getMerchantId();
            messageBodyPart.setContent(emailMessage, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            //MimeBodyPart attachPart = new MimeBodyPart();
            //attachPart.attachFile("/var/tmp/image19.png");
            //multipart.addBodyPart(attachPart);
            msg.setContent(multipart);
            Transport.send(msg);
            System.out.println(" email sent successfully using email template!");}
    }

    public List<OrderTable> getOrderLog(){
        return (List<OrderTable>) orderTableRepository.findAll();
    }

    public List<OrderDetails> getRecentOrders(String userId){
        return orderRepository.findAllByUserId(userId);

    }
    @Transactional
    @Override
    public void setRating(String productId, String merchantId, int rating) {
        orderRepository.setRating(rating,productId,merchantId);
        long sum = orderRepository.computeAvg(productId,merchantId);





    }

    @Override
    public OrderDetails transferFromCart(String userId) {
        OrderTable orderTable = new OrderTable();
        OrderDetails orderCreated = new OrderDetails();
        OrderTableDTO orderTableDTO = new OrderTableDTO();
        OrderTable orderTableCreated = saveOrder(orderTable);
        int orderId = orderTableCreated.getOrderId();
        List<CartDetails> details = cartService.getCartDetails(userId);
        for (CartDetails cartDetails : details
                ) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrderId(orderId);
            orderDetails.setMerchantId(cartDetails.getMerchantId());
            orderDetails.setPrice(cartDetails.getPrice());
            orderDetails.setUserId(userId);
            orderDetails.setRating(3.0);     //currently the rating is set to 3.0 .. We will ask the user for rating once the order is confirmend.
            orderDetails.setProductId(cartDetails.getProductId());
            orderDetails.setQuantity(cartDetails.getQuantity());
            orderCreated = saveDetail(orderDetails);
        }

        return orderCreated;
    }

        @Override
        public List<StockCheckDTO> checkStockAvailability (List <CartDetailsDTO> orderDetailsDTOList, String userId) {

            List<StockCheckDTO> stockCheckDTOList = new ArrayList<>();
            for (CartDetailsDTO orderDetailsDTO : orderDetailsDTOList) {
                StockCheckDTO stockCheckDTO = new StockCheckDTO();
                stockCheckDTO.setMerchantId(orderDetailsDTO.getMerchantId());
                stockCheckDTO.setProductId(orderDetailsDTO.getProductId());
                stockCheckDTO.setUserId(orderDetailsDTO.getUserId());
                stockCheckDTO.setQuantity(orderDetailsDTO.getQuantity());
                stockCheckDTO.setStatus(merchantFeign.checkStockFeign(orderDetailsDTO.getProductId(), Integer.parseInt(orderDetailsDTO.getMerchantId()), orderDetailsDTO.getQuantity()));
                stockCheckDTOList.add(stockCheckDTO);

            }

            int size = stockCheckDTOList.size();
            int count = 0;

            for (StockCheckDTO stockCheckDTO1 : stockCheckDTOList) {
                if (stockCheckDTO1.isStatus()) {
                    count++;
                }
            }
            if (count == size) {
                for (CartDetailsDTO orderDetailsDTO : orderDetailsDTOList) {

                        merchantFeign.updateStock(orderDetailsDTO.getProductId(), orderDetailsDTO.getMerchantId(), orderDetailsDTO.getQuantity());

                }
                transferFromCart(userId);


            }


            return stockCheckDTOList;

        }




    @Override
    public double merchantRating(String merchantId){
        List<OrderDetails> listOfOrders=orderRepository.findAllByMerchantId(merchantId);
        double averageRating=listOfOrders.stream().mapToDouble(OrderDetails::getRating).average().getAsDouble();
        merchantFeign.setMerchantRating(Integer.valueOf(merchantId),averageRating);
        return averageRating;
        //call merchantservice and set the merchant_rating there.....
    }


    @Override
    public double productRating(String productId){
        List<OrderDetails> listOfOrders=orderRepository.findAllByProductId(productId);
        double averageRating=listOfOrders.stream().mapToDouble(OrderDetails::getRating).average().getAsDouble();
        productFeign.setProductRating(productId,averageRating);
        return averageRating;
    }


    @Override
    @Transactional
    public void setOrderRating(int orderId, String productId, String merchantId, String userId, Double rating) {
        orderRepository.updateRating(rating,orderId,productId,merchantId,userId);
    }



    public int noOfProductsSold(String productId){
        List<OrderDetails> listOfOrders=orderRepository.findAllByProductId(productId);
        return listOfOrders.size();
    }





    }
