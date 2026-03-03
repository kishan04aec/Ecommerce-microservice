package com.ecommerce.order.repository;

import com.ecommerce.order.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>
{
    CartItem findByUserIdAndProductId(Long UserId, Long productId);

    void deleteByUserIdAndProductId(Long UserId, Long productId);

    List<CartItem> findByUserId(Long UserId);

    void deleteByUserId(Long UserId);
}
