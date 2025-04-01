<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 검색 폼 -->
<form id="searchForm" class="d-flex gap-2">
    <div class="flex-grow-1">
        <input type="text" class="form-control" name="keyword" id="keyword" placeholder="검색어를 입력하세요" value="${keyword}">
    </div>
    <button type="submit" class="btn btn-dark">검색</button>
</form>

<!-- 검색 결과 영역 -->
<div class="movie-container" id="searchResults">
    <%--<jsp:include page="searchResults.jsp"/>--%>
</div>

<div class="d-flex justify-content-center" id="searchPagination" style="margin-top: 20px">
</div>

<script>
    // 영화 검색 by 키워드
    const searchForm = document.getElementById('searchForm');
    const searchResults = document.getElementById('searchResults');
    const searchPagination = document.getElementById('searchPagination');

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

            searchResults.appendChild(movieCard);
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
        searchMovies(keyword, page);
    }
</script>
