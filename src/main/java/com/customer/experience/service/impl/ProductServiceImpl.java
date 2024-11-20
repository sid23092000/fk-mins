package com.customer.experience.service.impl;

import com.customer.experience.Utils.VernacWrapper;
import com.customer.experience.dto.ItemsDetailsDto;
import com.customer.experience.model.Items;
import com.customer.experience.model.Products;
import com.customer.experience.repository.ProductRepository;
import com.customer.experience.service.OrderService;
import com.customer.experience.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public List<Products> fetchProducts(int userId, List<ItemsDetailsDto> items) {

        List<Products> products=productRepository.findAll();
        VernacWrapper vernacWrapper=new VernacWrapper();
        return vernacWrapper.directMatcher(items,products);
    }

    @Override
    public List<Products> fetchRecommendedProducts(int userId, List<Products> products) {
        List<Products> recommendedProducts=new ArrayList<>();
        System.out.println(products.size());
        for(Products product:products)
        {
            List<Integer> similarProductIds=orderService.fetchProductIds(product.getName(), userId);
            if(similarProductIds==null)
            {
                List<Products> similarProducts=productRepository.findAllByName(product.getName());

                Products highestRatedProduct = Collections.max(similarProducts, Comparator.comparing(Products::getRating));
                recommendedProducts.add(highestRatedProduct);
            }
            else
            {
                List<Products> similarProducts=productRepository.findAllById(similarProductIds);
                Products highestRatedProduct = Collections.max(similarProducts, Comparator.comparing(Products::getRating));
                recommendedProducts.add(highestRatedProduct);
            }

        }

        return recommendedProducts;

    }
}
