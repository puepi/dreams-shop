package com.dailycodework.dreamshops.repository;

import com.dailycodework.dreamshops.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
