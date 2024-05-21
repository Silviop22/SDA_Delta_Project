package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CartRepo extends JpaRepository<Cart, Long> {

    @Query("SELECT c from Cart c where c.user.email = ?1 and c.cartId = ?2")
    Cart findCartByEmailAndCartId(String email, String cartId);

    @Query("SELECT c from Cart c join fetch c.cartItems ci join fetch ci.product p where p.productId = ?1")
    List<Cart> findCartsByProductId(Long productId);
}
