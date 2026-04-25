package com.MovieReview.MovieReview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailDTO {
    private Long id;
    private String title;
    private String description;
    private String genre;
    private String director;
    private Integer releaseYear;
    private Double averageRating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ReviewDTO> reviews;
}