package com.balako.onlinebookstore.repository.order;

import com.balako.onlinebookstore.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
            FROM Order o
            LEFT JOIN FETCH o.user u
            LEFT JOIN FETCH o.orderItems i
            LEFT JOIN FETCH i.book
            WHERE u.id = :id
            """)
    List<Order> findAllByUserId(Long id, Pageable pageable);

    @Query("""
            FROM Order o
            LEFT JOIN FETCH o.user
            LEFT JOIN FETCH o.orderItems i
            LEFT JOIN FETCH i.book
            WHERE o.id = :id
            """)
    Optional<Order> findByIdWithItems(Long id);
}
