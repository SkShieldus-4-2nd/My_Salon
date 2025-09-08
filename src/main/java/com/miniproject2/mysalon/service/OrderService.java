package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.OrderDTO;
import com.miniproject2.mysalon.entity.*;
import com.miniproject2.mysalon.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductDetailRepository productDetailRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public Long createOrder(OrderDTO.Request request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        long totalPrice = 0;
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (OrderDTO.OrderDetailDTO detailDTO : request.getOrderDetails()) {
            ProductDetail productDetail = productDetailRepository.findById(detailDTO.getProductDetailNum())
                    .orElseThrow(() -> new EntityNotFoundException("ProductDetail not found"));

            if (productDetail.getCount() < detailDTO.getCount()) {
                throw new IllegalArgumentException("재고가 부족합니다.");
            }

            productDetail.setCount(productDetail.getCount() - detailDTO.getCount());
            totalPrice += productDetail.getProduct().getPrice() * detailDTO.getCount();

            OrderProduct orderProduct = OrderProduct.builder()
                    .productDetail(productDetail)
                    .count(detailDTO.getCount())
                    .build();
            orderProducts.add(orderProduct);
        }

        Order order = Order.builder()
                .user(user)
                .totalPrice(totalPrice)
                .orderStatus(OrderStatus.ORDERED)
                .build();

        Order savedOrder = orderRepository.save(order);

        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.setOrder(savedOrder);
            orderProductRepository.save(orderProduct);
        }

        return savedOrder.getOrderNum();
    }

    public OrderDTO.Response getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found "));
        return OrderDTO.Response.fromEntity(order);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found "));

        if (order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new IllegalArgumentException("이미 배송된 상품은 취소가 불가능합니다.");
        }

        order.setOrderStatus(OrderStatus.CANCELED);

        for (OrderProduct orderProduct : order.getOrderProducts()) {
            orderProduct.getProductDetail().setCount(orderProduct.getProductDetail().getCount() + orderProduct.getCount());
        }
    }
}