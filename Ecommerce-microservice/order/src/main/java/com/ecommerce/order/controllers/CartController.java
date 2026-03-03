package com.ecommerce.order.controllers;

import lombok.RequiredArgsConstructor;
import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addTocart(@RequestHeader("X-User-ID") Long userId,
                                          @RequestBody CartItemRequest request){
        if (!cartService.addToCart(userId,request))
        {
            return ResponseEntity.badRequest().body("Product out of stock or user/product not found ");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

     @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long productId)
     {
      boolean deleted = cartService.deleteItemFromCart(userId, productId);

      return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
     }

     @GetMapping
    public ResponseEntity<List<CartItem>> getCart(@RequestHeader("X-User-ID") Long userId)
     {
   return ResponseEntity.ok(cartService.getCart(userId));
     }
}
