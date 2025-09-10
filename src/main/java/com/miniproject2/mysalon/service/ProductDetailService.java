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
    public ProductDetailDTO patchProductDetail(Long productDetailNum, ProductDetailDTO dto) {
        ProductDetail productDetail = productDetailRepository.findById(productDetailNum)
                .orElseThrow(() -> new EntityNotFoundException("ProductDetail", productDetailNum));

        if (dto.getProductNum() != null) {
            Product product = productRepository.findById(dto.getProductNum())
                    .orElseThrow(() -> new EntityNotFoundException("Product", dto.getProductNum()));
            productDetail.setProduct(product);
        }
        if (dto.getColor() != null) {
            productDetail.setColor(dto.getColor());
        }
        if (dto.getSize() != null) {
            productDetail.setSize(dto.getSize());
        }
        // Assuming count is a primitive int, we can check for a non-default value if needed, but typically it's always sent.
        // For simplicity, we'll allow 0 to be a valid patch value.
        productDetail.setCount(dto.getCount());

        if (dto.getImage() != null) {
            productDetail.setImage(dto.getImage());
        }

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

    public ProductDetailDTO getProductDetail(Long productNum, String size, String color) {
        ProductDetail productDetail = productDetailRepository.findByProduct_ProductNumAndSizeAndColor(productNum, size, color)
                .orElseThrow(() -> new EntityNotFoundException("ProductDetail not found with productNum: " + productNum + ", size: " + size + ", color: " + color));
        return ProductDetailDTO.fromEntity(productDetail);
    }

    @Transactional
    public ProductDetailDTO updateProductDetail(Long productNum, String size, String color, ProductDetailDTO.UpdateRequest request) {
        ProductDetail productDetail = productDetailRepository.findByProduct_ProductNumAndSizeAndColor(productNum, size, color)
                .orElseThrow(() -> new EntityNotFoundException("ProductDetail not found with productNum: " + productNum + ", size: " + size + ", color: " + color));

        productDetail.setColor(request.getColor());
        productDetail.setSize(request.getSize());
        productDetail.setCount(request.getCount());
        productDetail.setImage(request.getImage());

        return ProductDetailDTO.fromEntity(productDetail);
    }

    @Transactional
    public ProductDetailDTO patchProductDetail(Long productNum, String size, String color, ProductDetailDTO.PatchRequest request) {
        ProductDetail productDetail = productDetailRepository.findByProduct_ProductNumAndSizeAndColor(productNum, size, color)
                .orElseThrow(() -> new EntityNotFoundException("ProductDetail not found with productNum: " + productNum + ", size: " + size + ", color: " + color));

        if (request.getColor() != null) {
            productDetail.setColor(request.getColor());
        }
        if (request.getSize() != null) {
            productDetail.setSize(request.getSize());
        }
        if (request.getCount() != null) {
            productDetail.setCount(request.getCount());
        }
        if (request.getImage() != null) {
            productDetail.setImage(request.getImage());
        }

        return ProductDetailDTO.fromEntity(productDetail);
    }

    @Transactional
    public void deleteProductDetail(Long productNum, String size, String color) {
        ProductDetail productDetail = productDetailRepository.findByProduct_ProductNumAndSizeAndColor(productNum, size, color)
                .orElseThrow(() -> new EntityNotFoundException("ProductDetail not found with productNum: " + productNum + ", size: " + size + ", color: " + color));
        productDetailRepository.delete(productDetail);
    }
}
