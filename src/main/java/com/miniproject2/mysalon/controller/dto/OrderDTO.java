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
        private String userName;
        private LocalDateTime orderedAt;
        private List<OrderDetailResponse> orderDetails;

        public static OrderResponse fromEntity(Order order) {
            return OrderResponse.builder()
                    .orderNum(order.getOrderNum())
                    .userNum(order.getUser().getUserNum())
                    .userName(order.getUser().getUserName())
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
        private List<OrderListResponse> orderDetails;

        public static OrderResponse2 fromEntity(Order order) {
            return OrderResponse2.builder()
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
        private Long orderNum;//주문 번호
        private Long orderDetailNum;
        private String productName;
        private Long productDetailNum;
        private String description;
        private Long price;
        private String color;
        private String size;
        private String mainImage;
        private OrderStatus orderStatus;

        public static OrderListResponse fromEntity(OrderDetail orderDetail) {
            return OrderListResponse.builder()
                    .orderNum(orderDetail.getOrder().getOrderNum())
                    .orderDetailNum(orderDetail.getOrderDetailNum())
                    .productName(orderDetail.getProductDetail().getProduct().getProductName())
                    .productDetailNum(orderDetail.getProductDetail().getProductDetailNum())
                    .description(orderDetail.getProductDetail().getProduct().getDescription())
                    .price(orderDetail.getProductDetail().getProduct().getPrice())
                    .color(orderDetail.getProductDetail().getColor())
                    .size(orderDetail.getProductDetail().getSize())
                    .mainImage(orderDetail.getProductDetail().getProduct().getMainImage())
                    .orderStatus(orderDetail.getOrderStatus())
                    .build();
        }
    }
}
