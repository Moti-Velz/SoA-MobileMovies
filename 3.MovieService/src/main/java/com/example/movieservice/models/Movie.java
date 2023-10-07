package com.example.movieservice.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
public class Movie {
    @SerializedName("id")
    @NotNull
    private int id;

    @SerializedName("title")
    public String title;

    @SerializedName("genre_ids")
    private List<Integer> genre_ids;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("popularity")
    public double popularity;

    @SerializedName("poster_path")
    private String poster_path;


    public Movie(int id, @NotNull String title, ArrayList<Integer> genre_ids, String image, double popularity, String poster_path) {
        this.id = id;
        this.backdrop_path = image;
        this.genre_ids = genre_ids;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.title = title;
    }
}


