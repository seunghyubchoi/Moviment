package com.moviment.service;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

    private final String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NmIzODFiYWZmZDVlMWJjNjQ3MWM1NzhhMGRlMmNkZCIsIm5iZiI6MTczOTI1NDM1Mi45NTIsInN1YiI6IjY3YWFlYTUwN2M5OTEwODE0ZjliOWEyZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.pkH2ShDZuAAUGdq_FYntp-Xhy6u5b1DnSyeQ0IATohU";
    private final String baseUrl = "https://api.themoviedb.org/3/";

    @Override
        public void search(String keyword) {

        // HttpURLConnection
    }
}
