package com.example.movieservice.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpokenLanguage {
    public String english_name;
    @SerializedName("iso_639_1")
    public String iso639_1;
    public String name;
}
