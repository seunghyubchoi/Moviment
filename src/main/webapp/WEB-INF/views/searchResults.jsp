<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty movieList}">
    <div class="movie-container" id="movie-container">
        <ul>
            <c:forEach var="movie" items="${movieList}">
                <li>
                    <img src="https://image.tmdb.org/t/p/w500${movie.posterPath}" style="width: 100px" alt="${movie.title}">
                    <input type="hidden" name="movieId" value="${movie.id}">
                </li>
            </c:forEach>
        </ul>
    </div>

    <div class="page-container">
        <c:forEach var="i" begin="1" end="${totalPage}">
            <a href="#" class="page-link" data-page="${i}" data-content="${keyword}">${i}</a>
        </c:forEach>
    </div>
</c:if>

