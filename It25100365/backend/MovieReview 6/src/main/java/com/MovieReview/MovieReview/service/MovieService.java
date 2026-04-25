package com.MovieReview.MovieReview.service;

import com.MovieReview.MovieReview.dto.MovieDTO;
import com.MovieReview.MovieReview.dto.MovieDetailDTO;
import com.MovieReview.MovieReview.dto.ReviewDTO;
import com.MovieReview.MovieReview.entity.Movie;
import com.MovieReview.MovieReview.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieDTO createMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        movie.setDirector(movieDTO.getDirector());
        movie.setReleaseYear(movieDTO.getReleaseYear());
        movie.setAverageRating(0.0);

        Movie savedMovie = movieRepository.save(movie);
        return convertToDTO(savedMovie);
    }

    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MovieDetailDTO getMovieWithReviews(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + movieId));
        return convertToDetailDTO(movie);
    }

    public MovieDTO updateMovie(Long movieId, MovieDTO movieDTO) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + movieId));

        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        movie.setDirector(movieDTO.getDirector());
        movie.setReleaseYear(movieDTO.getReleaseYear());

        Movie updatedMovie = movieRepository.save(movie);
        return convertToDTO(updatedMovie);
    }

    public void deleteMovie(Long movieId) {
        movieRepository.deleteById(movieId);
    }

    private MovieDTO convertToDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setGenre(movie.getGenre());
        dto.setDirector(movie.getDirector());
        dto.setReleaseYear(movie.getReleaseYear());
        dto.setAverageRating(movie.getAverageRating());
        dto.setCreatedAt(movie.getCreatedAt());
        dto.setUpdatedAt(movie.getUpdatedAt());
        return dto;
    }

    private MovieDetailDTO convertToDetailDTO(Movie movie) {
        MovieDetailDTO dto = new MovieDetailDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setGenre(movie.getGenre());
        dto.setDirector(movie.getDirector());
        dto.setReleaseYear(movie.getReleaseYear());
        dto.setAverageRating(movie.getAverageRating());
        dto.setCreatedAt(movie.getCreatedAt());
        dto.setUpdatedAt(movie.getUpdatedAt());

        List<ReviewDTO> reviews = movie.getReviews().stream()
                .map(review -> {
                    ReviewDTO reviewDTO = new ReviewDTO();
                    reviewDTO.setId(review.getId());
                    reviewDTO.setReviewerName(review.getReviewerName());
                    reviewDTO.setRating(review.getRating());
                    reviewDTO.setComment(review.getComment());
                    reviewDTO.setCreatedAt(review.getCreatedAt());
                    reviewDTO.setUpdatedAt(review.getUpdatedAt());
                    reviewDTO.setMovieId(review.getMovie().getId());
                    return reviewDTO;
                })
                .collect(Collectors.toList());

        dto.setReviews(reviews);
        return dto;
    }
}