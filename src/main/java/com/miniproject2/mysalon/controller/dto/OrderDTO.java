package com.miniproject2.mysalon.controller.dto;

import com.miniproject2.mysalon.entity.Order;
import com.miniproject2.mysalon.entity.OrderProduct;
import com.miniproject2.mysalon.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotNull(message = "사용자 정보는 필수입니다.")
        private Long userId;
        @NotNull(message = "주문 상품 정보는 필수입니다.")
        private List<OrderDetailDTO> orderDetails;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long orderNum;
        private Long totalPrice;
        private OrderStatus orderStatus;
        private List<OrderDetailDTO> orderDetails;

        public static Response fromEntity(Order order) {
            return Response.builder()
                    .orderNum(order.getOrderNum())
                    .totalPrice(order.getTotalPrice())
                    .orderStatus(order.getOrderStatus())
                    .orderDetails(order.getOrderProducts().stream()
                            .map(OrderDetailDTO::fromEntity)
                            .collect(Collectors.toList()))
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderDetailDTO {
        private Long productDetailNum;
        private Integer count;
        private OrderStatus orderStatus;

        public static OrderDetailDTO fromEntity(OrderProduct orderProduct) {
            return OrderDetailDTO.builder()
                    .productDetailNum(orderProduct.getProductDetail().getProductDetailNum())
                    .count(orderProduct.getCount())
                    .orderStatus(orderProduct.getOrderStatus())
                    .build();
        }
    }
}