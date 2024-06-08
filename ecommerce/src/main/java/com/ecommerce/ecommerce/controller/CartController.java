package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.CartDTO;
import com.ecommerce.ecommerce.services.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "ecommerce")
public class CartController {
    @Autowired
    CartService cartService;


    @PostMapping("/public/carts/{cartId}/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId, @PathVariable Integer quantity) {
        CartDTO cartDTO = cartService.addProductToCart(cartId,productId,quantity);

        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }

    @GetMapping("/admin/carts")
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        List<CartDTO> cartDTOs = cartService.getAllCarts();
        return new ResponseEntity<List<CartDTO>>(cartDTOs, HttpStatus.OK);

    }

    @GetMapping("/public/users/{emailId}/carts/{cartId}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable String emailId, @PathVariable Long cartId) {
        CartDTO cartDTO = cartService.getCart(emailId,cartId);

        return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.OK);
    }

    @PutMapping("/public/carts/{cartId}/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long cartId, @PathVariable Long productId, @PathVariable Integer quantity) {
        CartDTO cartDTO = cartService.updateProductQuantityInCart(cartId,productId,quantity);

        return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.OK);
    }

    @DeleteMapping("/public/carts/{cartId}/products/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        String status = cartService.deleteProductFromCart(cartId,productId);

        return new ResponseEntity<String>(status, HttpStatus.OK);
    }
    @DeleteMapping("/admin/carts/{cartId}")
    public ResponseEntity<String> deleteCartWithCartId(@PathVariable Long cartId){
        String status = cartService.deleteCartWithCartId(cartId);
        return new ResponseEntity<String>(status, HttpStatus.OK);
    }
}
