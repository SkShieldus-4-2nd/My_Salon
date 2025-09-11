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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_detail_num")
    private ProductDetail productDetail;

    @Column(nullable = false)
    private Integer count;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_num")
    private Order order;

    //주문/배송 현황
    @Column
    @Enumerated(EnumType.STRING)  // enum 값을 문자열로 저장
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.ORDERED;

}
