package com.ecommerce.ecommerce.services;

import com.ecommerce.ecommerce.dto.CategoryDTO;
import com.ecommerce.ecommerce.dto.CategoryResponse;
import com.ecommerce.ecommerce.model.entity.Category;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public interface CategoryService {

    CategoryDTO createCategory(Category category);

    CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO updateCategory(Category category, Long categoryId);

    Boolean deleteCategory(Long categoryId);
}
