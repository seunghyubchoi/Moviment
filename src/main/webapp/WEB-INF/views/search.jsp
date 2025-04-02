<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 검색 폼 -->
<section id="sectionSection">
    <form id="searchForm" class="d-flex gap-2">
        <div class="flex-grow-1">
            <input type="text" class="form-control" name="keyword" id="keyword" placeholder="검색어를 입력하세요" value="${keyword}">
        </div>
        <button type="submit" class="btn btn-dark">검색</button>
    </form>
    <button id="prevPageBtn" class="btn btn-dark d-none">이전</button>
</section>


<!-- 검색 결과 영역 -->
<section class="movie-container" id="searchResults">
    <%--<jsp:include page="searchResults.jsp"/>--%>
</section>

<section class="d-flex justify-content-center" id="searchPagination" style="margin-top: 20px">
</section>

<script>
    // 영화 검색 by 키워드
    const sectionSection = document.getElementById('sectionSection');
    const searchForm = document.getElementById('searchForm');
    const searchResults = document.getElementById('searchResults');
    const searchPagination = document.getElementById('searchPagination');
    const prevPageBtn = document.getElementById('prevPageBtn');

    // 검색 버튼 클릭 시
    searchForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const keyword = document.getElementById('keyword').value;
        searchMovies(keyword);
    });

    // 영화 검색
    const searchMovies = (keyword, page = 1) => {

        fetch('/api/movies/' + encodeURIComponent(keyword) + '?page=' + page)
            .then(response => {
                if(!response.ok) {
                    return response.text()
                        .then(errorMessage => {
                            throw new Error(errorMessage);
                        })
                }
                return response.json();
            })
            .then(data => {
                createMovieCard(data);
                createPagination(data);
            })
            .catch(error => {
                console.log(error);
                alert("데이터를 불러오는 데 실패했습니다. \n잠시 후 다시 시도해주세요.");
            })
    }

    // 영화 카드 생성
    const createMovieCard = (data) => {

        const keyword = data.keyword;
        const currentPage = data.currentPage;

        searchResults.innerHTML = "";

        data.movieVOList.forEach(movie => {

            const movieCard = document.createElement('div');
            movieCard.classList.add('movie-card');

            const movieImg = document.createElement('img');
            movieImg.src = "https://image.tmdb.org/t/p/w500" + movie.posterPath;
            movieImg.alt = movie.title;

            const movieTitle = document.createElement('p');
            movieTitle.classList.add('fw-bold');
            movieTitle.innerText = movie.title;

            movieCard.appendChild(movieImg);
            movieCard.appendChild(movieTitle);

            movieCard.addEventListener('click', (e) => {
                e.preventDefault();
                moveToMovieDetail(movie.id, keyword, currentPage);
            })

            searchResults.appendChild(movieCard);
        });
    }

    // 영화 상세 페이지
    const moveToMovieDetail = (movieId, keyword, currentPage) => {
        fetch('/api/movies/detail/' + movieId + '?keyword=' + encodeURIComponent(keyword) + '&page=' + currentPage)
            .then(response => {
                if(!response.ok) {
                    return response.text()
                        .then(errorMessage => {
                            throw new Error(errorMessage);
                        })
                }
                return response.json();
            })
            .then(data => {
                console.log(data);

                const prevPage = data.targetPage;
                const movieVO = data.movieVO;
                const reviewVOList = data.reviewVOList;

                // 검색창 숨김, 이전버튼 표기
                hideSearchFormAndDisplayPrevPageBtn(keyword, prevPage);

                // 영화 상세 내용 표기
                showMovieDetail(movieVO);

                // 댓글 표기
                showReviewsSection(reviewVOList);

            })
    }

    const showReviewsSection = (movieVO, reviewVOList) => {
        showReviewInsertSection(movieVO);
        showReviewListSection(reviewVOList);
    }

    // 영화에 대한 댓글 입력
    const showReviewInsertSection = (movieVO) => {
        const reviewSection = document.createElement('div');
        reviewSection.classList.add('review-section');

        const reviewForm = document.createElement('form');

        const hiddenMovieId = document.createElement('input');
        hiddenMovieId.type = "hidden";
        hiddenMovieId.id = "movieId";
        hiddenMovieId.name = "movieId";
        hiddenMovieId.value = movieVO.id;
        reviewForm.appendChild(hiddenMovieId);

        const reviewTextarea = document.createElement('textarea');
        reviewTextarea.rows = 2;
        reviewTextarea.placeholder = "댓글을 입력하세요."
        reviewTextarea.required = true;
        reviewForm.appendChild(reviewTextarea);

        const reviewAddButton = document.createElement('button');
        reviewAddButton.innerHTML = "등록";
        reviewAddButton.classList.add("btn", "btn-dark");
        reviewAddButton.addEventListener('click', (e) => {
            e.preventDefault();
            const reviewText = reviewTextarea.value;
            const movieId = document.getElementById('movieId').value;

            if (!reviewText.trim()) {
                alert("댓글을 입력하세요.");
                return;
            }

            const reviewVO = {
                movieId: movieId,
                reviewText: reviewText
            };
        })

        reviewForm.appendChild(reviewAddButton);

        reviewSection.appendChild(reviewForm);
        searchResults.appendChild(reviewSection);
    }

    // 영화에 대한 댓글 목록
    const showReviewListSection = (reviewVOList) => {
        const reviewListSection = document.createElement('div');
        reviewListSection.classList.add('review-list')

        reviewVOList.forEach(reviewVO => {
            
        });
    }

    // 영화 상세 내용 표기
    const showMovieDetail = (movieVO) => {
        const movieDetail = document.createElement('div');
        movieDetail.classList.add('movie-detail');

        const movieImg = document.createElement('img');
        movieImg.src = "https://image.tmdb.org/t/p/w500" + movieVO.posterPath;
        movieImg.alt = movieVO.title;

        const movieInfo = document.createElement('div');
        movieInfo.classList.add('movie-info');

        const title = document.createElement('div');
        title.innerText = "제목 : " + movieVO.title;

        const overview = document.createElement('div');
        overview.innerHTML = "줄거리 : " + movieVO.overview;

        const releaseDate = document.createElement('div');
        releaseDate.innerHTML = "개봉일 : " + movieVO.releaseDate;

        const popularity = document.createElement('div');
        popularity.innerHTML = "인기도 : " + movieVO.popularity;

        const voteAverage = document.createElement('div');
        voteAverage.innerHTML = "평점 : " + movieVO.voteAverage;

        movieInfo.appendChild(title);
        movieInfo.appendChild(overview);
        movieInfo.appendChild(releaseDate);
        movieInfo.appendChild(popularity);
        movieInfo.appendChild(voteAverage);

        movieDetail.appendChild(movieImg);
        movieDetail.appendChild(movieInfo);

        searchResults.appendChild(movieDetail);
    }

    // 검색창 감추고 이전 버튼 표기
    const hideSearchFormAndDisplayPrevPageBtn = (keyword, prevPage) => {
        searchForm.classList.add("d-none");
        searchResults.innerHTML = "";
        searchPagination.innerHTML = "";

        prevPageBtn.classList.remove("d-none");
        prevPageBtn.addEventListener('click', (e) => {
            e.preventDefault();
            moveToPage(keyword, prevPage);
        });
    }

    // 영화 페이지네이션
    const createPagination = (data) => {

        let firstPage = data.firstPage;
        let currentPage = data.currentPage;
        let lastPage = data.lastPage;
        let keyword = data.keyword;
        let totalPages = data.totalPages;
        let pageGroup = data.pageGroup;

        searchPagination.innerHTML = "";

        const paginationList = document.createElement('ul');
        paginationList.classList.add('pagination');

        if(pageGroup > 1) {
            const prevPage = document.createElement('li');
            prevPage.classList.add("page-item");

            const prevPageLink = document.createElement('a');
            prevPageLink.classList.add("page-link");
            prevPageLink.href = "#";
            prevPageLink.innerHTML = "<";

            prevPageLink.addEventListener('click', (e) => {
                e.preventDefault();
                moveToPage(keyword, firstPage - 1);
            });

            prevPage.appendChild(prevPageLink);
            paginationList.appendChild(prevPage);
        }

        for(let i = firstPage; i <= lastPage; i++) {

            const paginationItem = document.createElement('li');
            paginationItem.classList.add('page-item');

            const paginationLink = document.createElement('a');
            paginationLink.classList.add('page-link');
            if(i === currentPage) {
                paginationLink.classList.add('active');
            }

            paginationLink.href = '#';
            paginationLink.innerText = i;

            paginationLink.addEventListener('click', (e) => {
                e.preventDefault();
                moveToPage(keyword, i);
            });

            paginationItem.appendChild(paginationLink); // a -> li
            paginationList.appendChild(paginationItem); // li -> ul
        }


        if(lastPage < totalPages) {
            const nextPage = document.createElement('li');
            nextPage.classList.add('page-item');

            const nextPageLink = document.createElement('a');
            nextPageLink.classList.add('page-link');
            nextPageLink.href = "#";
            nextPageLink.innerHTML = ">";

            nextPageLink.addEventListener('click', (e) => {
                e.preventDefault();
                moveToPage(keyword, lastPage + 1);
            });

            nextPage.appendChild(nextPageLink);
            paginationList.appendChild(nextPage);
        }

        searchPagination.appendChild(paginationList); // ul -> div
    }

    // 페이지 번호 이동
    const moveToPage = (keyword, page) => {
        searchForm.classList.remove("d-none");
        prevPageBtn.classList.add("d-none");
        searchMovies(keyword, page);
    }
</script>
