package com.movierental.controller;

import com.movierental.model.Movie;
import com.movierental.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) throws IOException {
        return ResponseEntity.ok(movieService.createMovie(movie));
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() throws IOException {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String id) throws IOException {
        Movie movie = movieService.getMovieById(id);
        return movie != null ? ResponseEntity.ok(movie) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam(required = false) String title,
                                                    @RequestParam(required = false) String genre) throws IOException {
        return ResponseEntity.ok(movieService.searchMovies(title, genre));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable String id, @RequestBody Movie movie) throws IOException {
        Movie updated = movieService.updateMovie(id, movie);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) throws IOException {
        return movieService.deleteMovie(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}