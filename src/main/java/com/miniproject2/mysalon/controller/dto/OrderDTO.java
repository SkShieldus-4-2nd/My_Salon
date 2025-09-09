package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.Order;
import com.miniproject2.mysalon.entity.OrderDetail;
import com.miniproject2.mysalon.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemDTO {
        private Long productDetailNum;
        private int count;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateOrderRequest {
        private Long userNum;
        private List<OrderItemDTO> orderItems;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateOrderRequest {
        private OrderStatus orderStatus;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderResponse {
        private Long orderNum;
        private Long userNum;
        private LocalDateTime orderedAt;
        private OrderStatus orderStatus;
        private List<OrderDetailResponse> orderDetails;

        public static OrderResponse fromEntity(Order order) {
            return OrderResponse.builder()
                    .orderNum(order.getOrderNum())
                    .userNum(order.getUser().getUserNum())
                    .orderedAt(order.getOrderedAt())
                    .orderStatus(order.getOrderStatus())
                    .orderDetails(order.getOrderProducts().stream()
                            .map(OrderDetailResponse::fromEntity)
                            .collect(Collectors.toList()))
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderDetailResponse {
        private Long orderDetailNum;
        private Long productDetailNum;
        private String productName;
        private int count;
        private Long price;
        private OrderStatus orderStatus;

        public static OrderDetailResponse fromEntity(OrderDetail orderDetail) {
            return OrderDetailResponse.builder()
                    .orderDetailNum(orderDetail.getOrderDetailNum())
                    .productDetailNum(orderDetail.getProductDetail().getProductDetailNum())
                    .productName(orderDetail.getProductDetail().getProduct().getProductName())
                    .count(orderDetail.getCount())
                    .price(orderDetail.getPrice())
                    .orderStatus(orderDetail.getOrderStatus())
                    .build();
        }
    }
}
