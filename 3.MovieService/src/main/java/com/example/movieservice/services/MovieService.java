package com.example.movieservice.services;

import com.example.movieservice.models.MovieDetailResponse;
import com.example.movieservice.models.MovieResponse;
import com.example.movieservice.services.IMovieService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Service
public class MovieService {
//    String url = "https://api.themoviedb.org/";
    private static final String authToken = System.getenv("tmdbKey");
    private IMovieService api;

    public MovieService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Authorization", "Bearer " + authToken)
                            .header("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.api = retrofit.create(IMovieService.class);
    }

    public IMovieService getApi() {
        return this.api;
    }
    public Call<MovieResponse> fetchPopularMovies(int page) {
        return api.fetchPopularMovies(page );
    }

    public Call<MovieResponse> fetchSimilarRecommendedMovies(int movieId) {
        return api.fetchSimilarRecommendedMovies(movieId );
    }

    public Call<MovieResponse> fetchByTitle(String query, int page) {
        return api.fetchByTitle(query, page);
    }

    public Call<MovieDetailResponse> fetchMovieDetails(int movieId, int page) {
        return api.fetchMovieDetails(movieId, page );
    }
}
