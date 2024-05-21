package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.entity.Cart;
import com.ecommerce.ecommerce.model.entity.CartItem;
import com.ecommerce.ecommerce.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {

    @Query("select ci.product from CartItem ci where ci.product.productId = ?1")
    Product findProductById(Long productId);

    @Query("SELECT ci from CartItem ci where ci.cart.cartId = ?1 and ci.product.productId = ?2")
    CartItem findCartItemByProductIdAndCartId(Long cartId, Long productId);

  	@Query("SELECT ci.cart FROM CartItem ci WHERE ci.cart.user.email = ?1 AND ci.cart.id = ?2")
    Cart findCartByEmailAndCartId(String email, Integer cartId);

    @Modifying
    @Query("DELETE from CartItem ci where ci.cart.cartId = ?1 and ci.product.productId = ?2")
    void deleteCartItemByProductIdaAndCartId(Long productId, Long cartId);

}
