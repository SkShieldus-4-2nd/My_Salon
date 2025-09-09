package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.ProductDetailDTO;
import com.miniproject2.mysalon.entity.Product;
import com.miniproject2.mysalon.entity.ProductDetail;
import com.miniproject2.mysalon.exception.EntityNotFoundException;
import com.miniproject2.mysalon.repository.ProductDetailRepository;
import com.miniproject2.mysalon.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductDetailService {

    private final ProductDetailRepository productDetailRepository;
    private final ProductRepository productRepository;

    @Transactional
    public ProductDetailDTO createProductDetail(ProductDetailDTO dto) {
        Product product = productRepository.findById(dto.getProductNum())
                .orElseThrow(() -> new EntityNotFoundException("Product", dto.getProductNum()));
        ProductDetail productDetail = dto.toEntity(product);
        ProductDetail savedDetail = productDetailRepository.save(productDetail);
        return ProductDetailDTO.fromEntity(savedDetail);
    }

    @Transactional
    public ProductDetailDTO updateProductDetail(Long productDetailNum, ProductDetailDTO dto) {
        ProductDetail productDetail = productDetailRepository.findById(productDetailNum)
                .orElseThrow(() -> new EntityNotFoundException("ProductDetail", productDetailNum));

        Product product = productRepository.findById(dto.getProductNum())
                .orElseThrow(() -> new EntityNotFoundException("Product", dto.getProductNum()));

        productDetail.setProduct(product);
        productDetail.setColor(dto.getColor());
        productDetail.setSize(dto.getSize());
        productDetail.setCount(dto.getCount());
        productDetail.setImage(dto.getImage());

        return ProductDetailDTO.fromEntity(productDetail);
    }

    @Transactional
    public void deleteProductDetail(Long productDetailNum) {
        if (!productDetailRepository.existsById(productDetailNum)) {
            throw new EntityNotFoundException("ProductDetail", productDetailNum);
        }
        productDetailRepository.deleteById(productDetailNum);
    }

    public ProductDetailDTO searchProductDetailByProductDetailNum(Long productDetailNum) {
        ProductDetail productDetail = productDetailRepository.findById(productDetailNum)
                .orElseThrow(() -> new EntityNotFoundException("ProductDetail", productDetailNum));
        return ProductDetailDTO.fromEntity(productDetail);
    }

    public List<ProductDetailDTO> searchProductDetailByProductNum(Long productNum) {
        return productDetailRepository.findByProduct_ProductNum(productNum).stream()
                .map(ProductDetailDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProductDetailDTO> searchProductDetailByUserNum(Long userNum) {
        return productDetailRepository.findByProduct_User_UserNum(userNum).stream()
                .map(ProductDetailDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProductDetailDTO> searchProductDetailByColor(String color) {
        return productDetailRepository.findByColor(color).stream()
                .map(ProductDetailDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProductDetailDTO> searchProductDetailByColorAndSize(String color, String size) {
        return productDetailRepository.findByColorAndSize(color, size).stream()
                .map(ProductDetailDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional // Add this annotation
    public List<ProductDetailDTO> getAllProductDetails() {
        return productDetailRepository.findAll().stream()
                .map(ProductDetailDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
