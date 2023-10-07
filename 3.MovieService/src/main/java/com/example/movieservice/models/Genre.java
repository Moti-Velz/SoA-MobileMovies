package com.example.movieservice.models;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
}
