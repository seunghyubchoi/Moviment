package com.moviment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviment.dto.ListAndPage;
import com.moviment.dto.SearchResult;
import com.moviment.dto.UserSessionDTO;
import com.moviment.exception.MovieException;
import com.moviment.model.MovieVO;
import com.moviment.model.ReviewVO;
import com.moviment.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

    private static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NmIzODFiYWZmZDVlMWJjNjQ3MWM1NzhhMGRlMmNkZCIsIm5iZiI6MTczOTI1NDM1Mi45NTIsInN1YiI6IjY3YWFlYTUwN2M5OTEwODE0ZjliOWEyZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.pkH2ShDZuAAUGdq_FYntp-Xhy6u5b1DnSyeQ0IATohU";
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final int pageSize = 10;
    private static final int groupSize = 5;

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private MovieRepository movieRepository;

    //@Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //@Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    //@Autowired
    public void setMovieRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public SearchResult searchMovies(String keyword, Model model) {
        String endPoint = "/search/movie?query=";
        String language = "&language=ko";
        String urlString = null;

        List<MovieVO> list = new ArrayList<>();

        int currentPage = 1;
        int totalPages = 1;

        try {
            while (currentPage <= totalPages) {
                urlString = BASE_URL + endPoint + keyword + language + "&page=" + currentPage;
                log.debug("요청 url : {}", urlString);

                // 직렬화된 결과 (JSON)
                HttpEntity<String> entity = new HttpEntity<String>(createHeaders());

                ResponseEntity<String> response = restTemplate.exchange(urlString, HttpMethod.GET, entity, String.class);

                if(!response.getStatusCode().is2xxSuccessful()) {
                    throw new MovieException("TMDB API 호출 실패 : " + response.getStatusCode());
                }

                if(response.getBody() == null) {
                    throw new MovieException("TMDB API 응답이 비어 있습니다.");
                }

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
                    log.debug("총 페이지 수: {}", totalPages);
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
    public List<MovieVO> getListOfMovieListByType(String movieListTypeInMain, int userPage) {
        String endPoint = "";

        if (movieListTypeInMain.equals("nowPlaying")) {
            endPoint = "/movie/now_playing?";
        } else if (movieListTypeInMain.equals("upComing")) {
            endPoint = "/movie/upcoming?";
        } else if (movieListTypeInMain.equals("topRated")) {
            endPoint = "/movie/top_rated?";
        }
        String language = "&language=ko";
        String page = "&page=";
        int tmdbPage = ((userPage - 1) / 2) + 1;
        if(tmdbPage < 1) {
            tmdbPage = 1;
        }
        //log.debug("userPage : {}", userPage);
        //log.debug("((userPage - 1) / 2) + 1 : {}", ((userPage - 1) / 2) + 1 );
        //log.debug("tmdbPage : {}", tmdbPage);
        String urlString = BASE_URL + endPoint + language + page + tmdbPage;
        //log.debug(urlString);

        // Spring 내 HTTP 요청 및 응답 전체(헤더 + 바디) 표현 클래스
        // 바디 없이 헤더만 전송하므로 Void가 더 명확
        HttpEntity<Void> entity = new HttpEntity<>(createHeaders()); // -> 생성자 인자에 HttpHeaders 넣으면 헤더만 가진 요청

        // RestTemplate 이용, 외부 API에 HTTP 요청
        ResponseEntity<String> response = restTemplate.exchange(urlString, HttpMethod.GET, entity, String.class);

        // 응답 본문 가져와 Jaskson으로 문자열을 트리구조의 JSON 객체(JsonNode)로 변환
        try {
            JsonNode root = objectMapper.readTree(response.getBody());

            // 위에서 만든 root 노드에서 results 라는 키를 가진 배열(JsonNode)을 꺼냄
            JsonNode results = root.get("results");

            List<MovieVO> movieList = new ArrayList<>();

            for (JsonNode result : results) {

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

                movieList.add(movieVO);
            }

            int startIndex = (userPage - 1) % 2 * 10;
            int endIndex = Math.min(startIndex + 10, movieList.size());
            //log.debug("startIndex : {}, endIndex : {}", startIndex, endIndex);
            return movieList.subList(startIndex, endIndex);

        } catch (Exception e) {
            throw new MovieException(e.getMessage());

        }
    }

    // 영화 검색
    @Override
    public ListAndPage searchMovies(String keyword, int userPage) {
        String endPoint = "/search/movie?query=";
        String language = "&language=ko";
        String page = "&page=";
        String urlString = null;
        int tmdbPage = ((userPage - 1) / 2) + 1;
        if(tmdbPage < 1) {
            tmdbPage = 1;
        }

        urlString = BASE_URL + endPoint + keyword + language + page + tmdbPage;
        log.debug("urlString : {}", urlString);
        List<MovieVO> list = new ArrayList<>();

        HttpEntity<Void> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<String> response = restTemplate.exchange(urlString, HttpMethod.GET, entity, String.class);
        log.debug("response.getBody() : {}", response.getBody());

        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new MovieException("TMDB API 호출 실패 : " + response.getStatusCode());
        }

        if(response.getBody() == null) {
            throw new MovieException("TMDB API 응답이 비어 있습니다.");
        }

        try {
            JsonNode root = objectMapper.readTree(response.getBody());

            log.debug("root : {}", root);

            JsonNode results = root.get("results");

            int totalResultsCount = Integer.parseInt(String.valueOf(root.get("total_results")));

            // pageSize = 10(한 페이지에 10)
            int totalPages = (int) Math.ceil((double)totalResultsCount / pageSize); // 전체 데이터 개수 / 한 페이지의 데이터 개수
            // 190개면 10개 나눴을 때 19페이지가 나옴

            int pageGroup = (int) Math.ceil((double)userPage / groupSize); // 1~5, 6~10
            // 사용자가 19페이지 선택, groupSize는 한 페이지네이션 당 보여줄 목록의 개수
            // 19 / 5 는 3.xx.. 올리면 4

            // 현재 그룹의 마지막 페이지
            int lastPage = pageGroup * groupSize; // 4 * 5 = 20

            if(lastPage > totalPages) { // 그런데 totalPages는 19, lastPage는 20일 때 20까지 보여줄 필요가 없기에
                lastPage = totalPages;
            }

            // 현재 그룹의 첫번째 페이지
            int firstPage = lastPage - groupSize + 1;
            if(firstPage < 1) { //firstPage = 3 - 5 + 1 = -1 (x)
                firstPage = 1;
            }

            List<MovieVO> movieList = new ArrayList<>();

            for (JsonNode result : results) {

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

                movieList.add(movieVO);
            }

            int startIndex = (userPage - 1) % 2 * 10;
            int endIndex = Math.min(startIndex + 10, movieList.size());

            //log.debug("startIndex : {}, endIndex : {}", startIndex, endIndex);
            return new ListAndPage(movieList, totalPages, pageGroup, userPage, firstPage, lastPage);
        } catch (Exception e) {
            throw new MovieException(e.getMessage());
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + API_KEY);
        return headers;
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
            String urlString = BASE_URL + endPoint + id + language;

            try {
                HttpEntity<String> entity = new HttpEntity<String>(createHeaders());

                ResponseEntity<String> response = restTemplate.exchange(urlString, HttpMethod.GET, entity, String.class);

                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new MovieException("TMDB API 호출 실패 : " + response.getStatusCode());
                }

                if (response.getBody() == null) {
                    throw new MovieException("TMDB API 응답이 비어 있습니다.");
                }

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
    public void addReview(UserSessionDTO user, ReviewVO review) {
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
