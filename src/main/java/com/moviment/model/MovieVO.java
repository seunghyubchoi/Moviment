package com.moviment.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MovieVO { // TMDB API
    private int id;
    private String title;
    private String overview;
    private String popularity;
    private String posterPath;
    private String releaseDate;
    private String voteAverage;
}
