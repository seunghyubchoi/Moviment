package com.moviment.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class MovieServiceImpl implements MovieService {

    private final String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NmIzODFiYWZmZDVlMWJjNjQ3MWM1NzhhMGRlMmNkZCIsIm5iZiI6MTczOTI1NDM1Mi45NTIsInN1YiI6IjY3YWFlYTUwN2M5OTEwODE0ZjliOWEyZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.pkH2ShDZuAAUGdq_FYntp-Xhy6u5b1DnSyeQ0IATohU";
    private final String baseUrl = "https://api.themoviedb.org/3";

    @Override
        public String search(String keyword) {
        System.out.println("MovieServiceImpl.search : " + keyword);
        String endPoint = "/search/movie?query=";
        String language = "&language=ko";

        String urlString = baseUrl + endPoint + keyword + language;
        System.out.println(urlString);

        // HttpURLConnection
        /*
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET"); // GET method
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println("Response : " + response);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        */
        // RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        // 직렬화된 결과 (JSON)
        //HttpEntity<String> entity = new HttpEntity<String>(headers);
        //ResponseEntity<String> responseEntity = restTemplate.exchange(urlString, HttpMethod.GET, entity, String.class);
        //System.out.println(responseEntity.getBody());

        // JSON -> JAVA
        String jsonResponse = restTemplate.getForObject(urlString, String.class); // DB JSON DATA를 문자열로
        System.out.println(jsonResponse);




        //return responseEntity.getBody();
        return null;
    }
}
