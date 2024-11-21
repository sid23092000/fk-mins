package com.customer.experience.service;

import com.customer.experience.dto.ItemsDetailsDto;
import com.customer.experience.dto.ListItemsDetailsDto;
import com.customer.experience.model.Items;
import com.customer.experience.model.Products;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ProductService {

    public List<Products> fetchProducts(int userId, List<ItemsDetailsDto> items);

    public List<Products> fetchRecommendedProducts(int userId, List<Products> products);
    public List<Products> findProductByName(String productName, int userId);
}
