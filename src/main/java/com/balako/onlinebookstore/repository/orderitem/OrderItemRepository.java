package com.balako.onlinebookstore.repository.orderitem;

import com.balako.onlinebookstore.model.OrderItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("""
            FROM OrderItem i
            WHERE i.order.id = :orderId
            AND i.order.user.id = :userId
            """)
    List<OrderItem> findAllByOrderIdAndUserId(Long orderId, Long userId, Pageable pageable);

    @Query("""
            FROM OrderItem i
            WHERE i.id = :orderItemId
            AND i.order.id = :orderId
            AND i.order.user.id = :userId
            """)
    Optional<OrderItem> findByIdAndOrderIdAndUserId(Long orderItemId, Long orderId, Long userId);
}
