package com.customer.experience.service;

import com.customer.experience.model.Items;
import com.customer.experience.model.Products;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ProductService {

    public List<Products> fetchProducts(int userId, List<Items> items);

    public List<Products> fetchRecommendedProducts(int userId, List<Products> products);
}
