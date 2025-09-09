package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.OrderDTO;
import com.miniproject2.mysalon.entity.*;
import com.miniproject2.mysalon.repository.OrderDetailRepository;
import com.miniproject2.mysalon.repository.OrderRepository;
import com.miniproject2.mysalon.repository.ProductDetailRepository;
import com.miniproject2.mysalon.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductDetailRepository productDetailRepository;
    private final OrderDetailRepository orderDetailRepository;

    // Create
    public Long createOrder(OrderDTO.CreateOrderRequest request) {
        User user = userRepository.findById(request.getUserNum())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Order order = Order.builder()
                .user(user)
                .orderedAt(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDERED)
                .orderProducts(new ArrayList<>()) // 초기화
                .build();

        for (OrderDTO.OrderItemDTO itemDto : request.getOrderItems()) {
            ProductDetail productDetail = productDetailRepository.findById(itemDto.getProductDetailNum())
                    .orElseThrow(() -> new EntityNotFoundException("ProductDetail not found"));

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .productDetail(productDetail)
                    .count(itemDto.getCount())
                    .price(productDetail.getProduct().getPrice()) // 현재 상품 가격으로 주문
                    .orderStatus(OrderStatus.ORDERED)
                    .build();
            order.getOrderProducts().add(orderDetail);
        }

        return orderRepository.save(order).getOrderNum();
    }

    // Read
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByUser(Long userNum) {
        return orderRepository.findByUser_UserNum(userNum);
    }

    // Update
    public Order updateOrder(Long orderId, OrderDTO.UpdateOrderRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setOrderStatus(request.getOrderStatus());
        return orderRepository.save(order);
    }

    // Delete
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    // Search by product name
    @Transactional(readOnly = true)
    public List<Order> searchOrdersByProductName(String query) {
        String[] keywords = query.split("\\s+");
        String keyword1 = keywords.length > 0 ? keywords[0] : null;
        String keyword2 = keywords.length > 1 ? keywords[1] : null;
        String keyword3 = keywords.length > 2 ? keywords[2] : null;
        return orderRepository.findByProductNameKeywords(keyword1, keyword2, keyword3);
    }

    // Search by user number
    @Transactional(readOnly = true)
    public List<Order> searchOrdersByUserNum(Long userNum) {
        return orderRepository.findByUser_UserNum(userNum);
    }

    // Search by product number
    @Transactional(readOnly = true)
    public List<Order> searchOrdersByProductNum(Long productNum) {
        return orderRepository.findByProductNum(productNum);
    }
}
