package com.moviment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviment.dto.SearchResult;
import com.moviment.exception.MovieException;
import com.moviment.model.MovieVO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NmIzODFiYWZmZDVlMWJjNjQ3MWM1NzhhMGRlMmNkZCIsIm5iZiI6MTczOTI1NDM1Mi45NTIsInN1YiI6IjY3YWFlYTUwN2M5OTEwODE0ZjliOWEyZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.pkH2ShDZuAAUGdq_FYntp-Xhy6u5b1DnSyeQ0IATohU";
    private final String baseUrl = "https://api.themoviedb.org/3";

    @Override
        public SearchResult searchMovies(String keyword, Model model) {
        System.out.println("MovieServiceImpl.search : " + keyword);
        String endPoint = "/search/movie?query=";
        String language = "&language=ko";
        String urlString = null;
        //String urlString = baseUrl + endPoint + keyword + language;
        //System.out.println(urlString);

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

        List<MovieVO> list = new ArrayList<>();

        int currentPage = 1;
        int totalPages = 1;

        try {

            while (currentPage <= totalPages && currentPage <= 500) {
                urlString = baseUrl + endPoint + keyword + language + "&page=" + currentPage;
                System.out.println("요청 url : " + urlString);
                // 직렬화된 결과 (JSON)
                HttpEntity<String> entity = new HttpEntity<String>(headers);
                ResponseEntity<String> response = restTemplate.exchange(urlString, HttpMethod.GET, entity, String.class);

                if(!response.getStatusCode().is2xxSuccessful()) {
                    throw new MovieException("TMDB API 호출 실패 : " + response.getStatusCode());
                }

                if(response.getBody() == null) {
                    throw new MovieException("TMDB API 응답이 비어 있습니다.");
                }

                ObjectMapper objectMapper = new ObjectMapper();

                JsonNode rootResults = objectMapper.readTree(response.getBody()); // 응답 데이터

                System.out.println(rootResults);

                JsonNode totalResults = rootResults.get("total_results");
                if(totalResults == null || totalResults.asInt() == 0) {
                    throw new MovieException("조회 결과가 없습니다.");
                }

                JsonNode results = rootResults.get("results");
                if(results == null || !results.isArray()) {
                    throw new MovieException("결과 데이터의 형식이 올바르지 않습니다.");
                }

                if (currentPage == 1) { // 첫 번째 요청에서 total_pages 값을 가져옴
                    totalPages = rootResults.get("total_pages").asInt();
                    if(totalPages >= 20) {
                        throw new MovieException("데이터가 많습니다. 검색어를 추가해주세요.");
                    }
                    System.out.println("총 페이지 수: " + totalPages);
                }

                for(JsonNode result : results) {
                    MovieVO movieVO = new MovieVO(
                            result.get("id").asInt(),
                            result.get("title").asText(),
                            result.get("overview").asText(),
                            result.get("popularity").asText(),
                            result.get("poster_path").asText(),
                            result.get("release_date").asText(),
                            result.get("vote_average").asText()
                    );
                    list.add(movieVO);
                }

                currentPage++;
            }
        } catch (Exception e) {
            throw new MovieException("JSON 처리 오류 : " + e.getMessage());
        }

        return new SearchResult(list, totalPages);
    }
}
