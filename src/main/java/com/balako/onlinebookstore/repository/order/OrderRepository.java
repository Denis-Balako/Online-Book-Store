package com.balako.onlinebookstore.repository.order;

import com.balako.onlinebookstore.model.Order;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
            FROM Order o
            LEFT JOIN FETCH o.user u
            LEFT JOIN FETCH o.orderItems i
            LEFT JOIN FETCH i.book
            WHERE u.id = :id
            """)
    List<Order> findAllByUserId(@Param("id") Long id, Pageable pageable);
}
