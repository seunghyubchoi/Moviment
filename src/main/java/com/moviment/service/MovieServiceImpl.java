package com.moviment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviment.dto.SearchResult;
import com.moviment.exception.MovieException;
import com.moviment.model.MovieVO;
import com.moviment.model.ReviewVO;
import com.moviment.model.UserVO;
import com.moviment.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NmIzODFiYWZmZDVlMWJjNjQ3MWM1NzhhMGRlMmNkZCIsIm5iZiI6MTczOTI1NDM1Mi45NTIsInN1YiI6IjY3YWFlYTUwN2M5OTEwODE0ZjliOWEyZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.pkH2ShDZuAAUGdq_FYntp-Xhy6u5b1DnSyeQ0IATohU";
    private final String baseUrl = "https://api.themoviedb.org/3";

    private MovieRepository movieRepository;

    @Autowired
    public void setMovieRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
        public SearchResult searchMovies(String keyword, Model model) {
        System.out.println("MovieServiceImpl.search : " + keyword);
        String endPoint = "/search/movie?query=";
        String language = "&language=ko";
        String urlString = null;

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
                            result.get("vote_average").asText(),
                            null
                    );
                    list.add(movieVO);
                }

                currentPage++;
            }
        } catch (Exception e) {
            throw new MovieException(e.getMessage());
        }

        return new SearchResult(list, totalPages);
    }

    @Override
    @Transactional
    public MovieVO searchDetail(int id, Model model) {
        // DB에 해당 ID의 영화 정보가 있는지 검색
        MovieVO movieVO = movieRepository.findMovieById(id);

        if(movieVO != null) {
            // 있으면 DB 정보 RETURN
            return movieVO;
        } else {
            // 없으면 DB에 불러온 API 정보 INSERT
            String endPoint = "/movie/";
            String language = "?language=ko";
            String urlString = baseUrl + endPoint + id + language;

            // RestTemplate
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");
            headers.set("Authorization", "Bearer " + apiKey);

            try {
                HttpEntity<String> entity = new HttpEntity<String>(headers);

                ResponseEntity<String> response = restTemplate.exchange(urlString, HttpMethod.GET, entity, String.class);

                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new MovieException("TMDB API 호출 실패 : " + response.getStatusCode());
                }

                if (response.getBody() == null) {
                    throw new MovieException("TMDB API 응답이 비어 있습니다.");
                }

                ObjectMapper objectMapper = new ObjectMapper();

                JsonNode results = objectMapper.readTree(response.getBody()); // 응답 데이터

                movieVO = new MovieVO(
                        results.get("id").asInt(),
                        results.get("title").asText(),
                        results.get("overview").asText(),
                        results.get("popularity").asText(),
                        results.get("poster_path").asText(),
                        results.get("release_date").asText(),
                        results.get("vote_average").asText(),
                        null
                );

                // DB 해당 MovieVO 정보 저장
                movieRepository.saveMovie(movieVO); // @Transactional

                return movieVO;
            } catch (Exception e) {
                throw new MovieException(e.getMessage());
            }
        }
    }

    @Override
    @Transactional
    public List<ReviewVO> searchReview(int id) {
        return movieRepository.searchReview(id);
    }

    @Override
    @Transactional
    public void addReview(UserVO user, ReviewVO review) {
        movieRepository.addReview(user, review);
    }

    @Override
    @Transactional
    public void deleteReview(ReviewVO review) {
        movieRepository.deleteReview(review);
    }

    @Override
    @Transactional
    public void patchReview(ReviewVO review) {
        movieRepository.patchReview(review);
    }
}
