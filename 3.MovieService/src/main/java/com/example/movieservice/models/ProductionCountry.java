package com.example.movieservice.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionCountry {
    @SerializedName("iso_3166_1")
    public String iso3166_1;
    public String name;
}

