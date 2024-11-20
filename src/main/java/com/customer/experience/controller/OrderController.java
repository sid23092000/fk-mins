package com.customer.experience.controller;

import com.customer.experience.dto.ApiResponse;
import com.customer.experience.dto.ListsDescDto;
import com.customer.experience.dto.OrdersDto;
import com.customer.experience.model.Products;
import com.customer.experience.service.ListService;
import com.customer.experience.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createOrderList(@RequestBody List<OrdersDto> ordersDto, @RequestHeader int userId) {
        try {
            orderService.createOrderList(ordersDto, userId);
            return new ResponseEntity<>(ApiResponse.success("List created successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Failed to create list"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/fetch")
    public ResponseEntity<ApiResponse<List<Integer>>> fetchProductIds(@RequestParam String prodName, @RequestHeader int userId) {
        try {
            List<Integer> ids = orderService.fetchProductIds(prodName, userId);
            ApiResponse<List<Integer>> response = new ApiResponse<>(HttpStatus.OK, "Data fetched successfully", ids);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
