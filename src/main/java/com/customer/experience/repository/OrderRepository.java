package com.customer.experience.repository;

import com.customer.experience.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByProdNameAndUserId(String prodName, int userId);
}
