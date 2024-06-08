package com.ecommerce.ecommerce.services;

import com.ecommerce.ecommerce.dto.CartDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CartService {

    CartDTO addProductToCart(Long cartId, Long productId, Integer quantity);

    List<CartDTO> getAllCarts();

    CartDTO getCart(String emailId, Long cartId);


    CartDTO updateProductQuantityInCart(Long cartId, Long productId, Integer quantity);

    void  updateProductsInCart(Long cartId, Long productId);

    String deleteProductFromCart(Long cartId, Long productId);

    String deleteCartWithCartId(Long cartId);
}
