package com.MovieReview.MovieReview.service;

import com.MovieReview.MovieReview.dto.ReviewDTO;
import com.MovieReview.MovieReview.entity.Movie;
import com.MovieReview.MovieReview.entity.Review;
import com.MovieReview.MovieReview.repository.MovieRepository;
import com.MovieReview.MovieReview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    public ReviewDTO createReview(Long movieId, ReviewDTO reviewDTO) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + movieId));

        Review review = new Review();
        review.setReviewerName(reviewDTO.getReviewerName());
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setMovie(movie);

        Review savedReview = reviewRepository.save(review);
        updateMovieAverageRating(movie);

        return convertToDTO(savedReview);
    }

    public List<ReviewDTO> getReviewsByMovieId(Long movieId) {
        return reviewRepository.findByMovieIdOrderByCreatedAtDesc(movieId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));

        review.setReviewerName(reviewDTO.getReviewerName());
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());

        Review updatedReview = reviewRepository.save(review);
        updateMovieAverageRating(review.getMovie());

        return convertToDTO(updatedReview);
    }

    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
        Movie movie = review.getMovie();
        reviewRepository.delete(review);
        updateMovieAverageRating(movie);
    }

    private void updateMovieAverageRating(Movie movie) {
        List<Review> reviews = reviewRepository.findByMovieId(movie.getId());
        if (reviews.isEmpty()) {
            movie.setAverageRating(0.0);
        } else {
            double averageRating = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);
            movie.setAverageRating(Math.round(averageRating * 10.0) / 10.0);
        }
        movieRepository.save(movie);
    }

    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setReviewerName(review.getReviewerName());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setUpdatedAt(review.getUpdatedAt());
        dto.setMovieId(review.getMovie().getId());
        return dto;
    }
}