<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .movie-container {
        display: flex;
        flex-wrap: wrap;
        gap: 15px;
        justify-content: center;
        min-height: 400px; 
        margin-bottom: 50px;
    }

    .movie-card {
        width: 150px;
        text-align: center;
        background: #fff;
        padding: 10px;
        border-radius: 8px;
        box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
    }

    .movie-card img {
        width: 100%;
        border-radius: 6px;
    }

    .page-container {
        display: flex;
        justify-content: center;
        padding-bottom: 20px;
    }

    .movie-detail {
        display: flex;
        align-items: flex-start;
        width: 100%;
        background: white;
        border-radius: 15px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
        padding: 20px;
        font-family: Arial, sans-serif;
        transition: transform 0.3s ease-in-out;
    }

    .movie-detail img {
        width: 200px;
        height: auto;
        border-radius: 10px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);
    }

    .movie-detail img:hover {
        transform: scale(1.05);
    }

    .movie-info {
        flex: 1;
        padding-left: 20px;
        text-align: left;
    }

    .movie-info div {
        margin-bottom: 10px;
        font-size: 16px;
    }

    .movie-info div:first-of-type {
        font-size: 20px;
        font-weight: bold;
        color: #333;
    }

    .movie-info div:last-of-type {
        font-weight: bold;
        color: #e67e22;
    }

    .review-section {
        margin-top: 20px;
        padding: 10px;
        background: #f9f9f9;
        border-radius: 8px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        width: 100%;
    }

    .review-section textarea {
        width: 100%;
        padding: 8px;
        border-radius: 5px;
        border: 1px solid #ccc;
        resize: none;
    }

    .review-section button {
        margin-top: 10px;
        background: #e67e22;
        color: white;
        border: none;
        padding: 8px 12px;
        border-radius: 5px;
        cursor: pointer;
    }

    .review-list {
        margin-top: 20px;
        padding: 10px;
        background: #fff;
        border-radius: 8px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        width: 100%;
    }

    .review {
        padding: 10px;
        border-bottom: 1px solid #ddd;
    }

    .review-date {
        font-size: 12px;
        color: gray;
    }

</style>

<c:if test="${not empty movieList}">
    <div class="movie-container" id="movie-container">
        <c:forEach var="movie" items="${movieList}">
            <div class="movie-card" data-content="${movie.id}">
                <img src="https://image.tmdb.org/t/p/w500${movie.posterPath}" alt="${movie.title}">
                <p class="fw-bold">${movie.title}</p>
            </div>
        </c:forEach>
    </div>

    <!-- 페이지네이션 -->
    <div class="page-container">
        <nav>
            <ul class="pagination">
                <c:forEach var="i" begin="1" end="${totalPage}">
                    <li class="page-item">
                        <a href="#" class="page-link" data-page="${i}" data-content="${keyword}">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
</c:if>

<c:if test="${not empty movieDetail}">
    <div class="movie-container" id="movie-container">
        <div class="movie-detail">
            <img src="https://image.tmdb.org/t/p/w500${movieDetail.posterPath}" alt="${movieDetail.title}">
            <div class="movie-info">
                <div>제목 : ${movieDetail.title}</div>
                <div>줄거리 : ${movieDetail.overview}</div>
                <div>개봉일 : ${movieDetail.releaseDate}</div>
                <div>인기도 : ${movieDetail.popularity}</div>
                <div>평점 : ${movieDetail.voteAverage}</div>
            </div>
        </div>

        <!-- 댓글 입력 폼 -->
        <div class="review-section">
            <h3>댓글 입력</h3>
            <form id="addReview">
                <input type="hidden" id="movieId" name="movieId" value="${movieDetail.id}">
                <textarea id="reviewContent" rows="2" placeholder="댓글을 입력하세요..." required></textarea>
                <button type="submit">등록</button>
            </form>
        </div>

        <div class="review-list">
            <h3>댓글 목록</h3>
            <c:forEach var="reivew" items="${movieDetail.reviews}">
                <div class="review">
                    <p><strong>${reivew.userId}</strong>: ${reivew.content}</p>
                    <p class="review-date">${reivew.createdAt}</p>
                </div>
            </c:forEach>
        </div>

    </div>
</c:if>
