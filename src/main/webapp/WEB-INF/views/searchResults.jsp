<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .movie-container {
        display: flex;
        flex-wrap: wrap;
        gap: 15px; /* 영화 카드 간격 */
        justify-content: center;
        min-height: 400px; /* ✅ 최소 높이 설정하여 footer와 겹치지 않도록 함 */
        margin-bottom: 50px; /* ✅ 페이지네이션과 간격 추가 */
    }

    .movie-card {
        width: 150px; /* 카드 크기 */
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
        margin-bottom: 20px; /* ✅ footer와의 간격 조정 */
        padding-bottom: 20px;
    }
</style>

<c:if test="${not empty movieList}">
    <div class="movie-container" id="movie-container">
        <c:forEach var="movie" items="${movieList}">
            <div class="movie-card">
                <img src="https://image.tmdb.org/t/p/w500${movie.posterPath}" alt="${movie.title}">
                <p class="fw-bold">${movie.title}</p>
                <input type="hidden" name="movieId" value="${movie.id}">
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
