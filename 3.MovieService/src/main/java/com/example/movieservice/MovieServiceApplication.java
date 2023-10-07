package com.example.movieservice;

import com.example.movieservice.models.Movie;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
@EnableWebMvc
public class MovieServiceApplication {

    public MovieServiceApplication() throws IOException {
    }

    public static void main(String[] args) {
        SpringApplication.run(MovieServiceApplication.class, args);
    }

}
