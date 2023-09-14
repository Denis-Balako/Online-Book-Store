package com.balako.onlinebookstore.repository.cart;

import com.balako.onlinebookstore.model.ShoppingCart;
import com.balako.onlinebookstore.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUser(User user);

    @Query("SELECT DISTINCT sc FROM ShoppingCart sc "
            + "LEFT JOIN FETCH sc.cartItems ci "
            + "LEFT JOIN FETCH ci.book "
            + "LEFT JOIN FETCH sc.user u WHERE u.id = :userId ")
    Optional<ShoppingCart> findByUserWithCartItems(@Param("userId") Long userId);
}
