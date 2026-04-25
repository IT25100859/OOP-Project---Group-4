package com.MovieReview.MovieReview.controller;

import com.MovieReview.MovieReview.dto.ReviewDTO;
import com.MovieReview.MovieReview.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/movie/{movieId}")
    public ResponseEntity<ReviewDTO> createReview(@PathVariable Long movieId, @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.createReview(movieId, reviewDTO);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByMovieId(@PathVariable Long movieId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByMovieId(movieId);
        return ResponseEntity.ok(reviews);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long reviewId, @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO updatedReview = reviewService.updateReview(reviewId, reviewDTO);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}