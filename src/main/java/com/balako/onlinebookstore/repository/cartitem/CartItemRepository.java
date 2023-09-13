package com.balako.onlinebookstore.repository.cartitem;

import com.balako.onlinebookstore.model.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT DISTINCT ci FROM CartItem ci "
            + "LEFT JOIN FETCH ci.book WHERE ci.id = :cartItemId")
    Optional<CartItem> findByIdWithBook(@Param("cartItemId") Long id);
}
