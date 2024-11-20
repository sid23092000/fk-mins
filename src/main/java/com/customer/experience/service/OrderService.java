package com.customer.experience.service;

import com.customer.experience.dto.OrdersDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface OrderService {
    void createOrderList(List<OrdersDto> ordersDto, int userId);
    List<Integer> fetchProductIds(String productName, int userId);
}
