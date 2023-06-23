package com.darksun.repository;

import com.darksun.model.OrderItem;
import com.darksun.model.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository< OrderItem, OrderItemId > {
	List< OrderItem > findByOrderCardId( Long orderCardId );
}
