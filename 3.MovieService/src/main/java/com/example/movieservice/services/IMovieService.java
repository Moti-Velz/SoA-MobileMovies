package com.example.movieservice.services;
import com.example.movieservice.models.MovieDetailResponse;
import com.example.movieservice.models.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Cette classe définie le contrat entre l'implémentation de mon service et l'api externe
 * Cela permet de découpler les composants
 */
public interface IMovieService {
        @Headers({"Accept: application/json"})
        @GET("3/movie/popular?language=fr")
        Call<MovieResponse> fetchPopularMovies(@Query("page") int page);

        @Headers({"Accept: application/json"})
        @GET("3/movie/{id}/recommendations?language=fr")
        Call<MovieResponse> fetchSimilarRecommendedMovies(@Path("id") int movieId);

        @Headers({"Accept: application/json"})
        @GET("3/search/movie?language=fr")
        Call<MovieResponse> fetchByTitle(@Query("query") String query, @Query("page") int page);

        @Headers({"Accept: application/json"})
        @GET("3/movie/{id}?language=fr")
        Call<MovieDetailResponse> fetchMovieDetails(@Path("id") int movieId, @Query("page") int page);

}
