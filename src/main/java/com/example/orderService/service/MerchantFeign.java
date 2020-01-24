package com.example.orderService.service;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "MerchantFeign" , url = "http://localhost:8085")
public interface MerchantFeign {
    @RequestMapping(method = RequestMethod.GET , value = "/productList/checkStock/{productId}/{merchantId}/{quantity}")
    boolean checkStockFeign(@PathVariable("productId") String productId,@PathVariable("merchantId") Integer merchantId,@PathVariable("quantity") Integer quantity );

    @RequestMapping(method = RequestMethod.POST , value = "/productList//updateStock/{productId}/{merchantId}/{quantity}")
    void updateStock(@PathVariable("productId") String productId,@PathVariable("merchantId") String merchantId,@PathVariable("quantity") int quantity);

    @RequestMapping(method = RequestMethod.POST , value = "/merchant/setMerchantRating/{id}/{rating}")
    boolean setMerchantRating(@PathVariable("id") Integer id,@PathVariable("rating") double rating);



}

