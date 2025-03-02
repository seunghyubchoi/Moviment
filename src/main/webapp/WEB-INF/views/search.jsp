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
<div id="searchResults" class="mt-4">
    <jsp:include page="searchResults.jsp"/>
</div>
