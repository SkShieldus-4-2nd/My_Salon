package com.miniproject2.mysalon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order-details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_num")
    private Long orderDetailNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_detail_num")
    private ProductDetail productDetail;

    @Column(nullable = false)
    private Integer count;

    @Column(nullable = false)
    private Long price; // 주문 시점의 가격


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_num")
    private Order order;

    //주문/배송 현황
    @Column
    @Enumerated(EnumType.STRING)  // enum 값을 문자열로 저장
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.ORDERED;

}
