package com.nab.ecommerce.repositories;

import com.nab.ecommerce.models.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItem,Integer> {
}
