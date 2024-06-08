package com.ecommerce.ecommerce.services;

import com.ecommerce.ecommerce.dto.ProductDTO;
import com.ecommerce.ecommerce.dto.ProductResponse;
import com.ecommerce.ecommerce.model.entity.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);


    ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy,
                                     String sortOrder);

    ProductDTO updateProduct(Long productId, Product product);

    ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy,
                                           String sortOrder);

    String deleteProduct(Long productId);



}
