package com.example.movieservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {

    private List<Movie> movieList;
    private List<User> userList;
    private User user;
    private Movie movie;
    private String message;
    private Object data;
    private String error;

}
