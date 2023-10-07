package com.example.movieservice.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetailResponse {
        public boolean adult;
        public String backdrop_path;
        public int budget;
        public ArrayList<Genre> genres;
        public String homepage;
        public int id;
        public String imdb_id;
        public String original_language;
        public String original_title;
        public String overview;
        public double popularity;
        public String poster_path;
        @SerializedName("production_companies")
        public ArrayList<ProductionCompany> productionCompanies;
        @SerializedName("production_countries")
        public ArrayList<ProductionCountry> productionCountries;
        public String release_date;
        public int revenue;
        public int runtime;
        @SerializedName("spoken_languages")
        public ArrayList<SpokenLanguage> spokenLanguages;
        public String status;
        public String tagline;
        public String title;
        public boolean video;
        public double vote_average;
        public int vote_count;
}
