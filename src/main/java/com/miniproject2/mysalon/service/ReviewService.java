package com.miniproject2.mysalon.service;

import com.miniproject2.mysalon.controller.dto.ReviewDTO;
import com.miniproject2.mysalon.entity.ProductDetail;
import com.miniproject2.mysalon.entity.Review;
import com.miniproject2.mysalon.entity.User;
import com.miniproject2.mysalon.repository.ProductDetailRepository;
import com.miniproject2.mysalon.repository.ReviewRepository;
import com.miniproject2.mysalon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductDetailRepository productDetailRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    //리뷰 생성
    @Transactional
    public ReviewDTO.Response createReview(Long userNum, Long productDetailNum, Short score, String text, MultipartFile reviewImage) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        ProductDetail productDetail = productDetailRepository.findById(productDetailNum)
                .orElseThrow(() -> new IllegalArgumentException("Product detail not found"));

        Review review = Review.builder()
                .user(user)
                .productDetail(productDetail)
                .text(text)
                .score(score)
                .build();

        Review savedReview = reviewRepository.save(review);

        if (reviewImage != null && !reviewImage.isEmpty()) {
            try {
                Path uploadPath = Paths.get(uploadDir, "review", String.valueOf(savedReview.getReviewNum()));
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFilename = reviewImage.getOriginalFilename();
                String extension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }

                String safeFilename = text.replaceAll("[^a-zA-Z0-9가-힣]", "_");
                if (safeFilename.length() > 30) {
                    safeFilename = safeFilename.substring(0, 30);
                }
                safeFilename = safeFilename + "_" + savedReview.getReviewNum() + extension;

                Path filePath = uploadPath.resolve(safeFilename);
                reviewImage.transferTo(filePath.toFile());

                String fileUrl = "/uploads/review/" + savedReview.getReviewNum() + "/" + safeFilename;
                savedReview.setReviewImage(fileUrl);
                reviewRepository.save(savedReview);

            } catch (IOException e) {
                throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
            }
        }

        return ReviewDTO.Response.fromEntity(savedReview);
    }

    // 리뷰 수정
    @Transactional
    public ReviewDTO.Response editReview(Long reviewNum, ReviewDTO.Request request) {
        Review review = reviewRepository.findById(reviewNum)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        review.setText(request.getText());
        review.setScore(request.getScore());
        review.setReviewImage(request.getReviewImage());

        return ReviewDTO.Response.fromEntity(reviewRepository.save(review));
    }

    // 리뷰 삭제
    @Transactional
    public void deleteReview(Long reviewNum) {
        if (!reviewRepository.existsById(reviewNum)) {
            throw new IllegalArgumentException("Review not found");
        }
        reviewRepository.deleteById(reviewNum);
    }


    //유저 아이디로 리뷰 조회
    @Transactional(readOnly = true)
    public List<ReviewDTO.Response> getUserReviews(Long userNum) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return reviewRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(ReviewDTO.Response::fromEntity)
                .toList();
    }



    // 특정 상품(Product) 기준 리뷰 전체 조회
    @Transactional(readOnly = true)
    public List<ReviewDTO.Response> getAllReviewsByProductNum(Long productNum) {
        return reviewRepository.findByProductNumOrderByCreatedAtDesc(productNum).stream()
                .map(ReviewDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }


    // 해당 상품(Product)에서 자신이 쓴 리뷰 로드
    @Transactional(readOnly = true)
    public List<ReviewDTO.Response> getUserReviewsByProduct(Long userNum, Long productNum) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return reviewRepository.findByUserAndProductNumOrderByCreatedAtDesc(user, productNum).stream()
                .map(ReviewDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    // 유저 스펙 기반 특정 상품(Product) 리뷰 조회
    @Transactional(readOnly = true)
    public List<ReviewDTO.Response> getReviewsByUserSpecForProduct(Long productNum,
                                                                   Short targetTall,
                                                                   Short targetWeight,
                                                                   Short tallRange,
                                                                   Short weightRange) {
        List<Review> productReviews = reviewRepository.findByProductNumOrderByCreatedAtDesc(productNum);

        return productReviews.stream()
                .filter(r -> {
                    Short userTall = r.getUser().getTall();
                    Short userWeight = r.getUser().getWeight();
                    boolean tallMatch = (userTall != null) && Math.abs(userTall - targetTall) <= tallRange;
                    boolean weightMatch = (userWeight != null) && Math.abs(userWeight - targetWeight) <= weightRange;
                    return tallMatch && weightMatch;
                })
                .map(ReviewDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    // 특정 상품(Product) 기준 별점 높은 순 리뷰 조회
    @Transactional(readOnly = true)
    public List<ReviewDTO.Response> getReviewsByProductSortedByScore(Long productNum) {
        return reviewRepository.findByProductNumOrderByScoreDescCreatedAtDesc(productNum).stream()
                .map(ReviewDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }


    // 특정 상품(Product) 기준 리뷰 점수 평균 조회
    @Transactional(readOnly = true)
    public Double getAverageScoreByProductNum(Long productNum) {
        Double avgScore = reviewRepository.findAverageScoreByProductNum(productNum);
        // 리뷰가 없으면 null 반환될 수 있으므로 0.0으로 처리
        return avgScore != null ? avgScore : 0.0;
    }

    // 유저가 작성한 리뷰 개수 반환
    @Transactional(readOnly = true)
    public Long getUserReviewCount(Long userNum) {
        User user = userRepository.findById(userNum)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return reviewRepository.countByUser(user);
    }

    // 특정 상품(Product) 리뷰 개수 반환
    @Transactional(readOnly = true)
    public Long getProductReviewCount(Long productNum) {
        return reviewRepository.countByProductNum(productNum);
    }


}


