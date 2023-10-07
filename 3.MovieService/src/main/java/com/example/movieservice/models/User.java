package com.example.movieservice.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("movies")
    private List<Movie> movies;
    @SerializedName("timestamp")
    private String timestamp;

}
