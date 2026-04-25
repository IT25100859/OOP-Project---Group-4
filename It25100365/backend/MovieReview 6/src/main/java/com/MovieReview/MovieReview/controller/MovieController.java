package com.MovieReview.MovieReview.controller;

import com.MovieReview.MovieReview.dto.MovieDTO;
import com.MovieReview.MovieReview.dto.MovieDetailDTO;
import com.MovieReview.MovieReview.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO) {
        MovieDTO createdMovie = movieService.createMovie(movieDTO);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<MovieDTO> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDetailDTO> getMovieWithReviews(@PathVariable Long movieId) {
        MovieDetailDTO movieDetail = movieService.getMovieWithReviews(movieId);
        return ResponseEntity.ok(movieDetail);
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long movieId, @RequestBody MovieDTO movieDTO) {
        MovieDTO updatedMovie = movieService.updateMovie(movieId, movieDTO);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long movieId) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.noContent().build();
    }
}