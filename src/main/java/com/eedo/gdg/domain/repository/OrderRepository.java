package com.eedo.gdg.domain.repository;

import com.eedo.gdg.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
