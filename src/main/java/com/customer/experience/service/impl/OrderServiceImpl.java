package com.customer.experience.service.impl;

import com.customer.experience.dto.OrdersDto;
import com.customer.experience.model.Orders;
import com.customer.experience.repository.OrderRepository;
import com.customer.experience.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Override
    public void createOrderList(List<OrdersDto> ordersDto, int userId) {
        List<Orders> ordersList = new ArrayList<>();

        for (OrdersDto order : ordersDto) {
            Orders orders = new Orders();
            orders.setUserId(userId);
            orders.setProdId(order.getProdId());
            orders.setProdName(order.getProdName());
            orders.setOrderDate(LocalDateTime.now());
            ordersList.add(orders);
        }
        orderRepository.saveAll(ordersList);

    }

    @Override
    public List<Integer> fetchProductIds(String productName, int userId){
        try {
            List<Orders> orders = orderRepository.findByProdNameAndUserId(productName, userId);
            List<Integer> ids = new ArrayList<>();
            for(Orders order : orders) {
                ids.add(order.getProdId());
            }
            return ids;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
