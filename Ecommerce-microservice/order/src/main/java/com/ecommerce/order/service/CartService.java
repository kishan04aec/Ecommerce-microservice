package com.ecommerce.order.service;

import com.ecommerce.order.clients.ProductServiceClients;
import com.ecommerce.order.dto.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

//    private final UserRepository userRepository;
//    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductServiceClients productServiceClients;

    public boolean addToCart(Long userId, CartItemRequest request) {
        ProductResponse productResponse  = productServiceClients.getProductDetails(String.valueOf(request.getProductId()));
        if (productResponse == null   ) {
            return false;
        }
       Integer stock =productResponse.getStockQuantity();
        if(stock == null || stock<  request.getQuantity())
            return false;

//        Optional <User>  userOpt = userRepository.findById(Long.valueOf(userId));
//        if (userOpt.isEmpty())
//            return false;
//
//        User user = userOpt.get();


        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId,request.getProductId());
        if(existingCartItem!=null) {
            // updating the cart value

            existingCartItem.setQuantity(existingCartItem.getQuantity()+request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000));
            cartItemRepository.save(existingCartItem);
        }
        else
        {
          CartItem cartItem = new CartItem();
          cartItem.setUserId(userId);
          cartItem.setProductId(request.getProductId());
          cartItem.setQuantity(request.getQuantity());
          cartItem.setPrice(BigDecimal.valueOf(1000));
          cartItemRepository.save(cartItem);
        }
       return true;

    }

    public boolean deleteItemFromCart(Long userId, Long productId) {

        CartItem cartItem= cartItemRepository.findByUserIdAndProductId(userId,productId);

        if (cartItem!=null) {
            cartItemRepository.deleteByUserIdAndProductId(userId,productId);
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
