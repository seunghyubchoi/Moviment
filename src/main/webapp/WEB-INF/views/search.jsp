<%@ page import="com.moviment.dto.UserSessionDTO" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    UserSessionDTO sessionUser = (UserSessionDTO) session.getAttribute("user");
    String sessionUserId = sessionUser != null ? sessionUser.getId() : "";
%>
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

    // 전역변수 keyword, currentPage => 댓글 등록 후 다시 사용하는 용도로 저장해둠
    let globalKeyword = "";
    let globalCurrentPage = 1;

    // 검색 버튼 클릭 시
    searchForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const keyword = document.getElementById('keyword').value;
        searchMovies(keyword);
    });

    // 영화 전체 검색
    const searchMovies = (keyword, page = 1) => {
        fetch('/api/movies/' + encodeURIComponent(keyword) + '?page=' + page)
            .then(handleResponse)
            .then(data => {
                createMovieCard(data);
                createPagination(data);
            })
            .catch(error => {
                console.log(error);
                alert("데이터를 불러오는 데 실패했습니다. \n잠시 후 다시 시도해주세요.");
            })
    }

    // 영화 상세 페이지 검색
    const moveToMovieDetail = (movieId, keyword, currentPage) => {
        currentPage = currentPage || 1;
        keyword = keyword || "";

        fetch('/api/movies/detail/' + movieId + '?keyword=' + encodeURIComponent(keyword) + '&page=' + currentPage)
            .then(handleResponse)
            .then(data => {
                const prevPage = data.targetPage;
                const movieVO = data.movieVO;
                const reviewVOList = data.reviewVOList;

                // 검색창 숨김, 이전버튼 표기
                hideSearchFormAndDisplayPrevPageBtn(keyword, prevPage);

                // 영화 상세 내용 표기
                showMovieDetail(movieVO);

                // 댓글 표기
                showReviewsSection(movieVO, reviewVOList);

            })
    }

    // 영화 카드 생성
    const createMovieCard = (data) => {

        globalKeyword = data.keyword;
        globalCurrentPage = data.currentPage;

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
            movieCard.onclick = null;
            movieCard.onclick = (e) => {
                e.preventDefault();
                moveToMovieDetail(movie.id, keyword, currentPage);
            }

            searchResults.appendChild(movieCard);
        });
    }

    // 댓글 표기
    const showReviewsSection = (movieVO, reviewVOList) => {
        showReviewInsertSection(movieVO);
        showReviewListSection(movieVO, reviewVOList);
    }

    // 영화에 대한 댓글 입력
    const showReviewInsertSection = (movieVO) => {
        const reviewSection = document.createElement('div');
        reviewSection.classList.add('review-section');

        const reviewForm = document.createElement('div');

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
        reviewAddButton.onclick = null;
        reviewAddButton.onclick = () => addReview(hiddenMovieId.value, reviewTextarea.value);

        reviewForm.appendChild(reviewAddButton);

        reviewSection.appendChild(reviewForm);
        searchResults.appendChild(reviewSection);
    }

    // 영화에 대한 댓글 목록
    const currentUserId = "<%=sessionUserId%>";
    const showReviewListSection = (movieVO, reviewVOList) => {
        const reviewListSection = document.createElement('div');
        reviewListSection.classList.add('review-list')

        reviewVOList.forEach(reviewVO => {
            const isWriter = reviewVO.userId === currentUserId;

            const reviewDiv = document.createElement('div');
            reviewDiv.classList.add('review-item');

            // 왼쪽 : 작성자 + 내용
            const contentDiv = document.createElement('div');
            contentDiv.classList.add('review-content');
            const contentP = document.createElement('p');
            const writerStrong = document.createElement('strong');
            writerStrong.textContent = reviewVO.userName;
            contentP.appendChild(writerStrong);
            contentP.append(" : " + reviewVO.content);
            contentDiv.appendChild(contentP);

            // 오른쪽 : 날짜 + 수정 버튼
            const dateDiv = document.createElement('div');
            dateDiv.classList.add('review-right');

            // 작성일
            const dateP = document.createElement('p');
            dateP.classList.add('review-date');
            dateP.textContent = reviewVO.createdAt;
            dateDiv.appendChild(dateP);
            // 수정 삭제 버튼
            if (isWriter) { // 본인이 쓴 글인 경우
                const buttonGroup = document.createElement('div');
                buttonGroup.classList.add('review-buttons');

                const editBtn = document.createElement('button');
                editBtn.className = 'btn btn-sm btn-primary me-2';
                editBtn.innerHTML = '<i class="bi bi-pencil"></i> 수정';

                editBtn.onclick = null;
                editBtn.onclick = () => patchReview(reviewVO.id);

                const deleteBtn = document.createElement('button');
                deleteBtn.className = 'btn btn-sm btn-danger';
                deleteBtn.innerHTML = '<i class="bi bi-trash"></i> 삭제';

                deleteBtn.onclick = null;
                deleteBtn.onclick = () => deleteReview(reviewVO.id);

                buttonGroup.appendChild(editBtn);
                buttonGroup.appendChild(deleteBtn);
                dateDiv.appendChild(buttonGroup);
            }
            // 버튼 추가
            reviewDiv.appendChild(contentDiv); // 왼쪽
            reviewDiv.appendChild(dateDiv);  // 오른쪽

            // 댓글 추가
            reviewListSection.appendChild(reviewDiv);
        });
        searchResults.appendChild(reviewListSection);
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
        prevPageBtn.onclick = null;
        prevPageBtn.onclick = (e) => {
            e.preventDefault();
            moveToPage(keyword, prevPage);
        }
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
            prevPageLink.onclick = null;
            prevPageLink.onclick = (e) => {
                e.preventDefault();
                moveToPage(keyword, firstPage - 1);
            }

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
            paginationLink.onclick = null;
            paginationLink.onclick = (e) => {
                e.preventDefault();
                moveToPage(keyword, i);
            }

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
            nextPageLink.onclick = null;
            nextPageLink.onclick = (e) => {
                e.preventDefault();
                moveToPage(keyword, lastPage + 1);
            }

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



/****************************
 영화 기능 관련 START
 ****************************/
// 댓글 추가
const addReview = (movieId, reviewContent) => {

    if (!reviewContent.trim()) {
        alert("댓글을 입력하세요.");
        return;
    }

    alert("댓글을 등록하시겠습니까?");

    fetch("/api/review", {
        method: "POST"
        , headers: {
            "Content-Type": "application/json"
        }
        , body: JSON.stringify({
            movieId: movieId
            , content: reviewContent
        })
    })
        .then(response => {
            if(!response.ok) {
                handleErrorResponse(response);
            }
            moveToMovieDetail(movieId, globalKeyword, globalCurrentPage);
        })
}

// 댓글 수정
const patchReview = (reviewId) => {
    const movieId = document.getElementById("movieId").value;
    let newContent = prompt("수정할 내용을 입력하세요");
    if (newContent !== null && newContent.trim() !== "") {
        fetch(`/api/review`, {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id: reviewId, content: newContent })
        })
            .then(response => {
                if(!response.ok) {
                    handleErrorResponse(response);
                }
                moveToMovieDetail(movieId, globalKeyword, globalCurrentPage);
            })
    }
}

// 댓글 삭제
const deleteReview = (reviewId) => {
    const movieId = document.getElementById("movieId").value;
    if(confirm("댓글을 삭제하시겠습니까?")) {
        fetch(`/api/review`, {
            method: "DELETE"
            , headers: {"Content-Type" : "application/json"}
            , body: JSON.stringify({id: reviewId})
        })
            .then(response => {
                if(!response.ok) {
                    handleErrorResponse(response);
                }
                moveToMovieDetail(movieId, globalKeyword, globalCurrentPage);
            })
    }
}


/****************************
 영화 기능 관련 END
 ****************************/




/****************************
 중복 구문 관련 START
 ****************************/

// fetch 호출 후 예외 발생 시 데이터에 대한 예외 text 리턴 or json 리턴
const handleResponse = (response) => {
    if (!response.ok) {
        const errorMessage = response.text();
        throw new Error(errorMessage);
    }
    return response.json();
};

// 댓글 추가, 수정, 삭제 도중 에러 발생 시 중복 예외 구문
const handleErrorResponse = (response) => {
    return response.text().then((errorMessage) => {
        throw new Error(errorMessage);
    });
};


/****************************
 중복 구문 관련 END
 ****************************/
</script>
