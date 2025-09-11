package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.OrderDTO;
import com.miniproject2.mysalon.entity.*;
import com.miniproject2.mysalon.exception.BusinessException;
import com.miniproject2.mysalon.exception.ErrorCode;
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
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Order order = Order.builder()
                .user(user)
                .orderedAt(LocalDateTime.now())
                .orderProducts(new ArrayList<>()) // 초기화
                .build();

        for (OrderDTO.OrderItemDTO itemDto : request.getOrderItems()) {
            ProductDetail productDetail = productDetailRepository.findById(itemDto.getProductDetailNum())
                    .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .productDetail(productDetail)
                    .count(itemDto.getCount())// 현재 상품 가격으로 주문
                    .orderStatus(OrderStatus.ORDERED)
                    .build();
            order.getOrderProducts().add(orderDetail);
        }

        return orderRepository.save(order).getOrderNum();
    }

    public OrderDTO.OrderCompleteResponse createOrder2(OrderDTO.CreateOrderRequest request) {
        User user = userRepository.findById(request.getUserNum())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Order order = Order.builder()
                .user(user)
                .orderedAt(LocalDateTime.now())
                .orderProducts(new ArrayList<>()) // 초기화
                .build();

        for (OrderDTO.OrderItemDTO itemDto : request.getOrderItems()) {
            ProductDetail productDetail = productDetailRepository.findById(itemDto.getProductDetailNum())
                    .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .productDetail(productDetail)
                    .count(itemDto.getCount())// 현재 상품 가격으로 주문
                    .orderStatus(OrderStatus.ORDERED)
                    .build();
            order.getOrderProducts().add(orderDetail);
        }
        Order savedOrder = orderRepository.save(order);
        return OrderDTO.OrderCompleteResponse.fromEntity(savedOrder);
    }

    // Read
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<OrderDTO.OrderResponse2> getAllOrders2(Long userId) {
        List<Order> orders = orderRepository.findByUserUserNum(userId);
        List<OrderDTO.OrderResponse2> response = orders.stream()
                .map(OrderDTO.OrderResponse2::fromEntity)
                .collect(Collectors.toList());
        return response;
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByUser(Long userNum) {
        return orderRepository.findByUser_UserNum(userNum);
    }

    // Update
    public Order updateOrder(Long orderId, OrderDTO.UpdateOrderRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
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
