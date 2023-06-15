package com.darksun.repository;

import com.darksun.model.OrderCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCardRepository extends JpaRepository< OrderCard, Long > {
}
