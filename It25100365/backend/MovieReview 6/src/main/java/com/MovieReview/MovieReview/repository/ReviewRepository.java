package com.MovieReview.MovieReview.repository;

import com.MovieReview.MovieReview.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMovieId(Long movieId);
    List<Review> findByMovieIdOrderByCreatedAtDesc(Long movieId);
    void deleteByMovieId(Long movieId);
}