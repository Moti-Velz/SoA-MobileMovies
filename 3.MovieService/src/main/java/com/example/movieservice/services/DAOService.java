package com.example.movieservice.services;

import com.example.movieservice.exceptions.BusinessLogicException;
import com.example.movieservice.exceptions.SystemException;
import com.example.movieservice.models.APIResponse;
import com.example.movieservice.models.Movie;
import com.example.movieservice.models.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

@Service
public class DAOService {

    private static final Logger log = LoggerFactory.getLogger(DAOService.class);
    private final IDAOService api;
    private static final String DAO_URL = "http://127.0.0.1:3002/v1/dao-api/";
    public DAOService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Authorization", "Bearer " + "test123")
                            .header("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DAO_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.api = retrofit.create(IDAOService.class);
    }
    // Overloaded constructor to allow for url changes
    public DAOService(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Authorization", "Bearer " + "test123")
                            .header("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.api = retrofit.create(IDAOService.class);
    }

    public IDAOService getApi() {
        return this.api;
    }

    //200, 404, 500
    public Movie saveMovieToFavorites(Movie movie, String username) {
        Call<APIResponse> call = api.saveMovie(movie, username);
        try {
            Response<APIResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body().getMovie();
            } else {
                // Log the error for further diagnosis
                log.error("Error saving movie with username: {}. Server responded with status: {} and message: {}",
                        username, response.code(), response.message());
                throw new BusinessLogicException("Error saving movie: " + response.message());
            }
        } catch (IOException e) {
            log.error("API call failed while saving movie for userId {}", username, e);
            throw new SystemException("API call failed: " + e.getMessage());
        }
    }

    //200, 404, 500
    public String deleteMovieFromFavorites(int movieId, String username) {
        Call<APIResponse> call = api.deleteMovie(username, movieId);
        try {
            Response<APIResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body().getMessage();
            } else {
                log.error("Error deleting movie with movieId {}. Server responded with status: {} and message: {}",
                        movieId, response.code(), response.message());
                throw new BusinessLogicException("Error deleting movie: " + response.message());
            }
        } catch (IOException e) {
            log.error("API call failed while deleting movie with movieId {}", movieId, e);
            throw new SystemException("API call failed: " + e.getMessage());
        }
    }

    //201, 409 si existing, 500
    public User createUser(User user) {
        Call<APIResponse> call = api.createUser(user);
        try {
            Response<APIResponse> response = call.execute();
            if(response.isSuccessful()) {
                return response.body().getUser();
            } else {
                log.error("Error creating user. Server responded with status: {} and message: {}",
                        response.code(), response.message());
                throw new BusinessLogicException("Error creating user: " + response.message());
            }
        } catch (IOException e) {
            log.error("API call failed while creating user", e);
            throw new SystemException("API call failed: " + e.getMessage());
        }
    }

    //200, 404, 500
    public User getUserByUsername(String username) {
        Call<APIResponse> call = api.getUserByUsername(username);
        try {
            Response<APIResponse> response = call.execute();
            if(response.isSuccessful()) {
                return response.body().getUser();
            } else {
                log.error("Error fetching user with username {}. Server responded with status: {} and message: {}",
                        username, response.code(), response.message());
                throw new BusinessLogicException("Error fetching user: " + response.message());
            }
        } catch (IOException e) {
            log.error("API call failed while fetching user with userId {}", username, e);
            throw new SystemException("API call failed: " + e.getMessage());
        }
    }

//200 si deleted 404 si not found sinon 500
    public boolean deleteUserByUsername(String username) {
        Call<APIResponse> call = api.deleteUserByUsername(username);
        try {
            Response<APIResponse> response = call.execute();
            if(response.isSuccessful()) {
                return true;
            } else {
                log.error("Error deleting user with username {}. Server responded with status: {} and message: {}",
                        username, response.code(), response.message());
                throw new BusinessLogicException("Error deleting user: " + response.message());
            }
        } catch (IOException e) {
            log.error("API call failed while deleting user with userId {}", username, e);
            throw new SystemException("API call failed: " + e.getMessage());
        }
    }


    //200, 500
    public List<User> getAllUser() {
        Call<APIResponse> call = api.getAllUser();
        try {
            Response<APIResponse> response = call.execute();
            if(response.isSuccessful()) {
                return response.body().getUserList();
            } else {
                log.error("Error fetching users. Server responded with status: {} and message: {}",
                        response.code(), response.message());
                throw new BusinessLogicException("Error fetching users: " + response.message());
            }
        } catch (IOException e) {
            log.error("API call failed while fetching all users", e);
            throw new SystemException("API call failed: " + e.getMessage());
        }
    }
}
