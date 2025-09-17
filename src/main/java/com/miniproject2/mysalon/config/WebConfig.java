package com.miniproject2.mysalon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5174", "http://localhost:5173")
                .allowedMethods("GET", "POST", "PATCH", "PUT","DELETE", "OPTIONS")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 기존 product 이미지 경로 유지
        String productPath = "file:///C:/uploads/";
        registry.addResourceHandler("/products/images/**")
                .addResourceLocations(productPath);

        // 리뷰 이미지 경로 추가
        String reviewPath = "file:///C:/MySalon/uploads/review/";
        registry.addResourceHandler("/reviews/images/**")
                .addResourceLocations(reviewPath);
    }

}
