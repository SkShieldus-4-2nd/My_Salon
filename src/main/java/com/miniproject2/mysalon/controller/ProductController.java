package com.miniproject2.mysalon.controller;

import com.miniproject2.mysalon.repository.ProductRepository;
import com.miniproject2.mysalon.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
}
