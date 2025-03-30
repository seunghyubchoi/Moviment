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
<script>
    // 영화 검색 by 키워드
    const searchForm = document.getElementById('searchForm');
    const searchResults = document.getElementById('searchResults');

    searchForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const keyword = document.getElementById('keyword').value;
        fetch('api/movies/' + encodeURIComponent(keyword))
            .then(response => {
                console.log("!!!!");
                console.log(response);
                /*
                if(!response.ok) {
                    return response.text()
                        .then(errorMessage => {
                            throw new Error(errorMessage);
                        })
                }
                return response.json();
                
                 */
            })
            /*.then(data => {
                searchResults.innerHTML = "";

                data.forEach(movie => {
                    const movieCard = document.createElement('div');
                    movieCard.classList.add('movie-card');

                    const movieImg = document.createElement('img');
                    movieImg.src = "https://image.tmdb.org/t/p/w500" + movie.posterPath;
                    movieImg.alt = movie.title;

                    const movieTitle = document.createElement('p');
                    movieTitle.classList.add('fw-bold');

                    movieImg.appendChild(movieTitle);
                    movieCard.appendChild(movieImg);

                });
            })
            .catch(error => {
                console.log(error);
                alert("데이터를 불러오는 데 실패했습니다. \n잠시 후 다시 시도해주세요.");
            })*/
    });
</script>
