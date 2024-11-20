package com.customer.experience.service.impl;

import com.customer.experience.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    public List<Integer> fetchProductIds(String productName, int userId){
        List<Integer> demo=new ArrayList<>();
        demo.add(10);
        demo.add(11);
        return demo;
    }
}
