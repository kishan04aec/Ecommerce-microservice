package com.ecommerce.order.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

   private Long productId;
   private Long userId;
//    @ManyToOne
//    @JoinColumn(name="user_id", nullable = false)
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name= "product_id", nullable = false)
//    private Product product;

    private Integer quantity;

    private BigDecimal price;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @CreationTimestamp
    private Timestamp createdAt;

}
