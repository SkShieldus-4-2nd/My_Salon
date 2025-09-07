package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.repository.OrderRepository;
import com.miniproject2.mysalon.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
}
