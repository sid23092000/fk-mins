package com.customer.experience.repository;


import com.customer.experience.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {
    List<Products> findAllByName(String name);
}
