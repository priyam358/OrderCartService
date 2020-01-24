package com.example.orderService.service;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "ProductFeign" , url = "http://localhost:8086")
public interface ProductFeign {
    @RequestMapping(method = RequestMethod.POST , value ="/product/setProductRating/{productId}/{rating}")
    boolean setProductRating(@PathVariable("productId") String productId, @PathVariable("rating") double rating);

}
