package com.customer.experience.controller;

import com.customer.experience.dto.ApiResponse;
import com.customer.experience.dto.ListItemsDetailsDto;
import com.customer.experience.model.Items;
import com.customer.experience.model.Products;
import com.customer.experience.repository.ProductRepository;
import com.customer.experience.service.ListService;
import com.customer.experience.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/product")
public class ProductController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ListService listService;

    @GetMapping("/fetch")
    public ResponseEntity<List<Products>> fetchProducts(@RequestParam int listId, @RequestHeader int userId) {

        try {


            ListItemsDetailsDto listItems= listService.fetchListItems(listId);

            List<Products> products=productService.fetchProducts(userId, listItems.getItems() );
            List<Products> recommendedProducts=productService.fetchRecommendedProducts(userId, products);
            return new ResponseEntity<>(recommendedProducts, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error in fetching the actual products");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/similar")
    public ResponseEntity<List<Products>> fetchSimilarProducts(@RequestParam int productId, @RequestHeader int userId) {

        try {
          Products product = productRepository.findById(productId).orElse(null);
            if (product != null)
            {
                List<Products> similarProducts = productRepository.findAllByName(product.getName());
                return new ResponseEntity<>(similarProducts, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            System.out.println("Error in fetching the similar products");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Products>> getAllProducts(@RequestHeader int userId) {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{prodName}")
    public ResponseEntity<ApiResponse<List<Products>>> getProductByName(@PathVariable("prodName") String prodName, @RequestHeader int userId) {
        try {
            List<Products> product = productService.findProductByName(prodName, userId);
            if (product != null) {
                ApiResponse<List<Products>> response = new ApiResponse<>(HttpStatus.OK, "Data fetched successfully", product);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ApiResponse.error("Product not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Failed to fetch product"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
