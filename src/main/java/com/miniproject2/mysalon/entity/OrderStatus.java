package com.miniproject2.mysalon.entity;

public enum OrderStatus {
    ORDERED,          // 주문 완료
    PAYMENT_CONFIRMED,// 결제 확인됨
    PREPARING_SHIPMENT,// 배송 준비 중
    SHIPPED,          // 택배사 도착/배송 시작
    IN_TRANSIT,       // 배송 중
    DELIVERED,        // 배송 완료
    RETURN_REQUESTED, // 환불/반품 요청됨
    RETURNED,         // 환불/반품 완료
    CANCELLED         // 주문 취소
}
