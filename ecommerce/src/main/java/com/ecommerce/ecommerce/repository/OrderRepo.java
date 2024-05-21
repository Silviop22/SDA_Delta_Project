package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {

    @Query("SELECT o from Order o WHERE o.email = ?1 AND o.orderId = ?2")
    Order findOrderByEmailAndOrdersId(String email, Long cartId);

    List<Order> findAllByEmail(String emailId);
}
