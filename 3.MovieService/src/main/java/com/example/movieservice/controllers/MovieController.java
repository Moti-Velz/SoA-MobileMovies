package com.example.movieservice.controllers;

import com.example.movieservice.services.DAOService;
import com.example.movieservice.models.Movie;
import com.example.movieservice.models.MovieDetailResponse;
import com.example.movieservice.models.MovieResponse;
import com.example.movieservice.models.User;
import com.example.movieservice.services.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RequestMapping("api/v1")
public class MovieController {

    private final MovieService movieService;
    private final DAOService daoService;


    public MovieController(MovieService movieService, DAOService daoService) {
        this.movieService = movieService;
        this.daoService = daoService;
    }

    @GetMapping("/movies/popular")
    @Operation(summary = "Get popular movies", description = "Fetches a list of popular movies")
    public ResponseEntity<?> getPopularMovies() throws IOException {
        MovieResponse response = movieService.fetchPopularMovies(1).execute().body();
        if(response == null) {
            return ResponseEntity.status(500).body("Un problème est survenu lors de la récupération des films.");
        }
        return ResponseEntity.ok(response.getMovies());
    }

    @GetMapping("/movies/title-search/{search}")
    @Operation(summary = "Search movie by title", description = "Fetches a list of movie by title")
    public ResponseEntity<?> getByTitle(@PathVariable String search) throws IOException {

        List<Movie> res = movieService.fetchByTitle(search, 1).execute().body().getMovies();
        if(res.isEmpty()) {
            return ResponseEntity.ok("Aucun film ne correspond à votre recherche");
        } else {
            return ResponseEntity.ok(res);
        }
    }

    @GetMapping("/movies/{movieId}")
    @Operation(summary = "Get a movie's details", description = "Fetches a movie's detail object")
    public ResponseEntity<?> getMovieDetail(@PathVariable int movieId) throws IOException {
        MovieDetailResponse res = movieService.fetchMovieDetails(movieId, 1).execute().body();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/movies/similar/{movieId}")
    @Operation(summary = "Get similar movies.", description = "Fetches a movie's similar titles.")
    public ResponseEntity<?> getSimilarMovie(@PathVariable int movieId) throws IOException {
        List<Movie> list = movieService.fetchSimilarRecommendedMovies(movieId).execute().body().getMovies();
         return ResponseEntity.ok(list);
    }

    // Début des routes protégées :
    @PostMapping("/users/{username}/movies")
    @Operation(summary = "Save a movie to favorites.", description = "Saves a movie to the users account.")
    public ResponseEntity<?> saveMovie(@RequestBody Movie movie, @PathVariable String username) throws IOException {

        Movie res = daoService.saveMovieToFavorites(movie, username);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/users/{username}/movies/{movieId}")
    @Operation(summary = "Remove from favorites", description = "Deletes a movie from a user's account")
    public ResponseEntity<?> deleteMovie(@PathVariable Integer movieId, @PathVariable String username) throws IOException {
        String res = daoService.deleteMovieFromFavorites(movieId, username);
        return ResponseEntity.ok(res);
    }


    // TODO Refactoriser dans un controlleur séparé les méthodes suivantes:
    @PostMapping("/users") // register
    @Operation(summary = "Create account.", description = "Creates a user's account")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = daoService.createUser(user);
        if (createdUser != null) {
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } else {
            // In an actual application, you might want to provide more context about the failure
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{username}") // Login
    @Operation(summary = "Get user by username", description = "Fetches a user based on username.")
    public ResponseEntity<?> getUserById(@PathVariable("username") String username) {
        User user;
        try {
            user = daoService.getUserByUsername(username);
            System.out.println(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Une erreur est survenue", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{username}")
    @Operation(summary = "Delete user's account", description = "Deletes a user's account")
    public ResponseEntity<String> deleteUserById(@PathVariable("username") String username) {
        boolean result = daoService.deleteUserByUsername(username);
        if (result) {
            return new ResponseEntity<>("User No " + username + " deleted successfully.", HttpStatus.OK);
        } else {
            // You can refine the error handling based on the message,
            // but for simplicity, we will treat it as a generic error here
            return new ResponseEntity<>("User No " + username + " not deleted successfully.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/AllUsers")
    @Operation(summary = "Get All Users", description = "Fetches all users.")
    public ResponseEntity<?> getAllUser() {
        List<User> userList = daoService.getAllUser();
        if (userList != null) {
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
