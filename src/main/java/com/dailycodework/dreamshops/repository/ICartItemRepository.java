package com.dailycodework.dreamshops.repository;

import com.dailycodework.dreamshops.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartItemRepository extends JpaRepository<CartItem,Long> {

    void deleteAllByCartId();
}
