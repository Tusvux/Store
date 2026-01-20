package com.sales.controller;

import com.sales.dto.CartItemRequest;
import com.sales.model.Cart;
import com.sales.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/customer/{customerId}")
    public Cart getCart(@PathVariable Long customerId) {
        return cartService.getCart(customerId);
    }

    @PostMapping("/customer/{customerId}/items")
    public Cart addItemToCart(@PathVariable Long customerId, @RequestBody CartItemRequest request) {
        return cartService.addItemToCart(customerId, request);
    }

    @PutMapping("/customer/{customerId}/items/{productId}")
    public Cart updateCartItem(@PathVariable Long customerId, 
                               @PathVariable Long productId, 
                               @RequestParam Integer quantity) {
        return cartService.updateCartItem(customerId, productId, quantity);
    }

    @DeleteMapping("/customer/{customerId}/items/{productId}")
    public Map<String, String> removeItemFromCart(@PathVariable Long customerId, @PathVariable Long productId) {
        cartService.removeItemFromCart(customerId, productId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Đã xóa sản phẩm khỏi giỏ hàng");
        return response;
    }

    @DeleteMapping("/customer/{customerId}")
    public Map<String, String> clearCart(@PathVariable Long customerId) {
        cartService.clearCart(customerId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Đã xóa toàn bộ giỏ hàng");
        return response;
    }

    @GetMapping("/customer/{customerId}/total")
    public Map<String, Double> getCartTotal(@PathVariable Long customerId) {
        return cartService.getCartTotal(customerId);
    }
}
