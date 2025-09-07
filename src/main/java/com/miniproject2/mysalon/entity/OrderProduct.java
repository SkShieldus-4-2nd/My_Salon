package com.miniproject2.mysalon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_num")
    private Long orderProductNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_num")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_num")
    private Order order;
}
