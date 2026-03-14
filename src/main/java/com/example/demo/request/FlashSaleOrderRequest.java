package com.example.demo.request;


import lombok.Data;

@Data
public class FlashSaleOrderRequest {

    private Long productId;

    private Long userId;

    private int quantity;

}