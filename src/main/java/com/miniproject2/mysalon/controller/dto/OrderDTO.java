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
    public static class OrderCompleteResponse {
        private Long orderNum;
        private String userName;
        private LocalDateTime orderedAt;
        private OrderStatus orderStatus;

        public static OrderCompleteResponse fromEntity(Order order) {
            return OrderCompleteResponse.builder()
                    .orderNum(order.getOrderNum())
                    .userName(order.getUser().getUserName())
                    .orderedAt(order.getOrderedAt())
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
                    .orderStatus(orderDetail.getOrderStatus())
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderResponse2 {
        private Long orderNum;
        private Long userNum;
        private LocalDateTime orderedAt;
        private OrderStatus orderStatus;
        private List<OrderListResponse> orderDetails;

        public static OrderResponse2 fromEntity(Order order) {
            return OrderResponse2.builder()
                    .orderNum(order.getOrderNum())
                    .userNum(order.getUser().getUserNum())
                    .orderedAt(order.getOrderedAt())
                    .orderDetails(order.getOrderProducts().stream()
                            .map(OrderListResponse::fromEntity)
                            .collect(Collectors.toList()))
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderListResponse {
        private Long orderDetailNum;
        private Long productDetailNum;
        private String productName;
        private String description;
        private int count;
        private Long price;
        private OrderStatus orderStatus;

        public static OrderListResponse fromEntity(OrderDetail orderDetail) {
            return OrderListResponse.builder()
                    .orderDetailNum(orderDetail.getOrderDetailNum())
                    .productDetailNum(orderDetail.getProductDetail().getProductDetailNum())
                    .productName(orderDetail.getProductDetail().getProduct().getProductName())
                    .description(orderDetail.getProductDetail().getProduct().getDescription())
                    .count(orderDetail.getCount())
                    .orderStatus(orderDetail.getOrderStatus())
                    .build();
        }
    }
}
