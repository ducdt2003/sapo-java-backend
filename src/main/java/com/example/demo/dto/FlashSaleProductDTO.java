package com.example.demo.dto;

import lombok.Data;

@Data
public class FlashSaleProductDTO {

    private Long id;
    private String name;
    private double originalPrice;
    private double flashSalePrice;
    private int stock;

}
