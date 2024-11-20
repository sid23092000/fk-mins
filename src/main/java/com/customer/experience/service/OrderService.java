package com.customer.experience.service;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface OrderService {
    public List<Integer> fetchProductIds(String productName, int userId);
}
