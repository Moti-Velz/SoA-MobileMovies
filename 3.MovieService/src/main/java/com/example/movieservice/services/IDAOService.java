package com.example.movieservice.services;

import com.example.movieservice.models.APIResponse;
import com.example.movieservice.models.Movie;
import com.example.movieservice.models.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface IDAOService {
    @Headers({"Accept: application/json"})
    @POST("/users/{username}/movies")
    Call<APIResponse> saveMovie(@Body Movie movie, @Path("username") String username);

    @Headers({"Accept: application/json"})
    @DELETE("/users/{username}/movies/{movieId}")
    Call<APIResponse> deleteMovie( @Path("username") String username, @Path("movieId") int movieId);

    @Headers({"Accept: application/json"})
    @POST("/users")
    Call<APIResponse> createUser(@Body User user);

    @Headers({"Accept: application/json"})
    @GET("/users/{username}")
    Call<APIResponse> getUserByUsername(@Path("username") String username);

    @Headers({"Accept: application/json"})
    @DELETE("/users/{username}")
    Call<APIResponse> deleteUserByUsername(@Path("username") String username);

    @Headers({"Accept: application/json"})
    @GET("/users")
    Call<APIResponse> getAllUser();
}
