package com.example.orderService.service.implementation;

import com.example.orderService.entity.OrderDetails;
import com.example.orderService.entity.OrderTable;
import com.example.orderService.repository.OrderRepository;
import com.example.orderService.repository.OrderTableRepository;
import com.example.orderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import sun.jvm.hotspot.debugger.AddressException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class OrderServiceImplementation implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderTableRepository orderTableRepository;


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
        return orderRepository.userDetails(merchantId,productId);
    }

    @Override
    public void sendMail() throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("priyam.shah@coviam.com", "nokialumia625");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("sportycartyteam6@gmail.com","samika98"));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("samikakashyap@gmail.com"));
        msg.setSubject("SportyCart Order Summary");
        msg.setContent("Tutorials point email", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Tutorials point email", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        attachPart.attachFile("/var/tmp/image19.png");
        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }


}
