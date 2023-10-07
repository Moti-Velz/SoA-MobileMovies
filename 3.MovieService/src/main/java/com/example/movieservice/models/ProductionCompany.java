package com.example.movieservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionCompany {
    public int id;
    public String logo_path;
    public String name;
    public String origin_country;
}
