package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.config.AppConstants;
import com.ecommerce.ecommerce.dto.CategoryDTO;
import com.ecommerce.ecommerce.dto.CategoryResponse;
import com.ecommerce.ecommerce.model.entity.Category;
import com.ecommerce.ecommerce.services.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "ecommerce")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @PostMapping("/admin/category")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO createCategory(@Valid @RequestBody Category category) {
        return categoryService.createCategory(category);
    }


    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDERS_BY) String sortOrder) {
        CategoryResponse categoryResponse = categoryService.getCategories(pageNumber, pageSize,sortBy,sortOrder);

        return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategories(@RequestBody Category category, @PathVariable Long categoryId) {
        CategoryDTO categoryDTO = categoryService.updateCategory(category,categoryId);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }
    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        boolean deletionSuccessful = categoryService.deleteCategory(categoryId);

        if (deletionSuccessful) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}
