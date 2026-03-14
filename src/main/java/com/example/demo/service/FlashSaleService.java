package com.example.demo.service;

import com.example.demo.dto.FlashSaleOrderRequest;
import com.example.demo.entity.FlashSaleOrder;
import com.example.demo.entity.Product;
import com.example.demo.repository.FlashSaleOrderRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FlashSaleService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FlashSaleOrderRepository flashSaleOrderRepository;

    @Transactional
    public String order(FlashSaleOrderRequest request) {

        Long productId = request.getProductId();
        Long userId = request.getUserId();
        int quantity = request.getQuantity();

        if (quantity <= 0) {
            throw new RuntimeException("Hãy nhập số lượng cần mua");
        }
        Product product = productRepository.findByIdForUpdate(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        if (product.getStock() <= 0) {
            throw new RuntimeException("Sản phẩm đã hết hàng");
        }

        if (product.getStock() < quantity) {
            throw new RuntimeException("Số lượng mua vượt quá sản phẩm trong kho");
        }

        int bought = flashSaleOrderRepository.sumQuantityByUserId(userId);

        if (bought + quantity > 2) {
            throw new RuntimeException("Bạn chỉ được mua tối đa 2 sản phẩm trong Flash Sale");
        }

        int totalSold = flashSaleOrderRepository.sumTotalQuantity();

        if (totalSold + quantity > 500) {
            throw new RuntimeException("Flash Sale đã hết 500 sản phẩm");
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        FlashSaleOrder order = new FlashSaleOrder();
        order.setProductId(productId);
        order.setUserId(userId);
        order.setQuantity(quantity);

        flashSaleOrderRepository.save(order);

        return "Đặt hàng thành công";
    }

    public Page<Product> getFlashSaleProducts(Pageable pageable) {
        return productRepository.findByFlashSaleTrue(pageable);
    }
}