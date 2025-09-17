package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.controller.dto.OrderDTO;
import com.miniproject2.mysalon.entity.Order;
import com.miniproject2.mysalon.security.CurrentUser;
import com.miniproject2.mysalon.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAuthority('SELLER') or hasAuthority('BUYER')")
    public ResponseEntity<Long> createOrder(@CurrentUser Long userNum, @RequestBody OrderDTO.CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request, userNum));
    }


    @PostMapping("/create")
    @PreAuthorize("hasAuthority('SELLER') or hasAuthority('BUYER')")
    public ResponseEntity<OrderDTO.OrderCompleteResponse> createOrder2(@CurrentUser Long userNum, @RequestBody OrderDTO.CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder2(request, userNum));
    }



    @GetMapping("/count")
    @PreAuthorize("hasAuthority('SELLER') or hasAuthority('BUYER')")
    public ResponseEntity<Long> getOrdersCount(@CurrentUser Long userNum) {
        Long count = orderService.getOrdersCount(userNum);
        return ResponseEntity.ok(count);
    }


    @GetMapping
    public ResponseEntity<List<OrderDTO.OrderResponse>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderDTO.OrderResponse> response = orders.stream()
                .map(OrderDTO.OrderResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/")
    @PreAuthorize("hasAuthority('SELLER') or hasAuthority('BUYER')")
    public ResponseEntity<List<OrderDTO.OrderResponse2>> getAllOrdersByUser(@CurrentUser Long userId) {
        List<OrderDTO.OrderResponse2> orders = orderService.getAllOrders2(userId);

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user2/")
    @PreAuthorize("hasAuthority('SELLER') or hasAuthority('BUYER')")
    public ResponseEntity<List<OrderDTO.OrderResponse>> getOrdersByUser(@CurrentUser Long userNum) {
        List<Order> orders = orderService.getOrdersByUser(userNum);
        List<OrderDTO.OrderResponse> response = orders.stream()
                .map(OrderDTO.OrderResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO.OrderResponse> updateOrder(@PathVariable Long orderId, @RequestBody OrderDTO.UpdateOrderRequest request) {
        Order updatedOrder = orderService.updateOrder(orderId, request);
        return ResponseEntity.ok(OrderDTO.OrderResponse.fromEntity(updatedOrder));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderDTO.OrderResponse> patchOrder(@PathVariable Long orderId, @RequestBody OrderDTO.UpdateOrderRequest request) {
        Order updatedOrder = orderService.updateOrder(orderId, request);
        return ResponseEntity.ok(OrderDTO.OrderResponse.fromEntity(updatedOrder));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/by-name")
    public ResponseEntity<List<OrderDTO.OrderResponse>> searchOrdersByProductName(@RequestParam String query) {
        List<Order> orders = orderService.searchOrdersByProductName(query);
        List<OrderDTO.OrderResponse> response = orders.stream()
                .map(OrderDTO.OrderResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/by-user/{userNum}")
    public ResponseEntity<List<OrderDTO.OrderResponse>> searchOrdersByUserNum(@PathVariable Long userNum) {
        List<Order> orders = orderService.searchOrdersByUserNum(userNum);
        List<OrderDTO.OrderResponse> response = orders.stream()
                .map(OrderDTO.OrderResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/by-product/{productNum}")
    public ResponseEntity<List<OrderDTO.OrderResponse>> searchOrdersByProductNum(@PathVariable Long productNum) {
        List<Order> orders = orderService.searchOrdersByProductNum(productNum);
        List<OrderDTO.OrderResponse> response = orders.stream()
                .map(OrderDTO.OrderResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
