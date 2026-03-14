package com.example.demo.repository;

import com.example.demo.entity.FlashSaleOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FlashSaleOrderRepository extends JpaRepository<FlashSaleOrder, Long> {

    @Query("SELECT COALESCE(SUM(o.quantity),0) FROM FlashSaleOrder o WHERE o.userId = :userId")
    int sumQuantityByUserId(Long userId);

    @Query("SELECT COALESCE(SUM(o.quantity),0) FROM FlashSaleOrder o")
    int sumTotalQuantity();
}