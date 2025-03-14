<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
            <c:forEach var="review" items="${reviewList}">
                <div class="review">
                    <p><strong>${review.userName}</strong>: ${review.content}</p>
                    <p class="review-date">${review.createdAt}</p>

                    <c:if test="${sessionScope.userId eq review.userId}">
                        <div>
                            <button class="btn btn-sm btn-primary me-2" onclick="patchReview(${review.id})">
                                <i class="bi bi-pencil"></i> 수정
                            </button>
                            <button class="btn btn-sm btn-danger" onclick="deleteReview(${review.id})">
                                <i class="bi bi-trash"></i> 삭제
                            </button>
                        </div>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </div>
</c:if>
