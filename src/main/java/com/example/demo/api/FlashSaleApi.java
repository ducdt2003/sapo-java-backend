package com.example.demo.api;

import com.example.demo.Response.ApiResponse;
import com.example.demo.dto.FlashSaleOrderRequest;
import com.example.demo.entity.Product;
import com.example.demo.service.FlashSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flash-sale")
public class FlashSaleApi {

    @Autowired
    private FlashSaleService flashSaleService;

    @GetMapping("/products")
    public ResponseEntity<?> flashSalePage(
            @RequestParam(defaultValue = "0") int page) {

        Page<Product> productPage =
                flashSaleService.getFlashSaleProducts(PageRequest.of(page, 12));

        return ResponseEntity.ok(productPage);
    }

    @PostMapping("/order")
    public ResponseEntity<ApiResponse<String>> order(@RequestBody FlashSaleOrderRequest request) {

        String result = flashSaleService.order(request);

        return ResponseEntity.ok(
                new ApiResponse<>("success", result, null)
        );
    }


}