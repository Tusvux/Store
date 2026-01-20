package com.sales.service;

import com.sales.dto.OrderRequest;
import com.sales.exception.BadRequestException;
import com.sales.exception.ResourceNotFoundException;
import com.sales.model.Customer;
import com.sales.model.Order;
import com.sales.model.OrderItem;
import com.sales.model.Product;
import com.sales.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Đơn hàng không tồn tại"));
    }

    @Transactional
    public Order createOrder(OrderRequest request) {
        Customer customer = customerService.getCustomerById(request.getCustomerId());

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setShippingAddress(request.getShippingAddress());
        order.setPaymentMethod(request.getPaymentMethod());

        double totalAmount = 0.0;

        for (OrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            Product product = productService.getProductById(itemRequest.getProductId());

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new BadRequestException("Sản phẩm " + product.getName() + " không đủ số lượng");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice());
            order.getItems().add(orderItem);

            totalAmount += product.getPrice() * itemRequest.getQuantity();

            product.setStock(product.getStock() - itemRequest.getQuantity());
            productService.updateProduct(product.getId(), product);
        }

        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrderStatus(Long id, String status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    @Transactional
    public void cancelOrder(Long id) {
        Order order = getOrderById(id);
        
        if (order.getStatus().equals("COMPLETED")) {
            throw new BadRequestException("Không thể hủy đơn hàng đã hoàn thành");
        }

        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productService.updateProduct(product.getId(), product);
        }

        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }
}
