package com.ecommerce.ecommerce.dto;

import com.ecommerce.ecommerce.model.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long cartId;
    private List<CartItem> cartItems = new ArrayList<>();
    private Double totalPrice = 0.0;


    public void setProducts(List<ProductDTO> products) {
    }
}
