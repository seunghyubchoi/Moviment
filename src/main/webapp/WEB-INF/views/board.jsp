<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2 class="mb-4">📌 게시판</h2>

<table class="table table-hover table-bordered text-center">
    <thead class="table-dark">
    <tr>
        <th width="10%">글번호</th>
        <th width="40%">제목</th>
        <th width="20%">작성자</th>
        <th width="15%">등록일</th>
        <th width="15%">조회수</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${not empty boardList}">
        <c:forEach var="board" items="${boardList}">
            <tr>
                <td>${board.id}</td>
                <td><a href="#" class="text-decoration-none">${board.title}</a></td>
                <td>${board.userId}</td>
                <td>${board.createdAt}</td>
                <td>${board.viewCount}</td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
<div class="d-flex justify-content-center mt-3">
    <button type="button" class="btn btn-dark" onclick="loadContentPage('addBoard')">새글 등록</button>
</div>
