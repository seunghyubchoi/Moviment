<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form id="searchForm">
    <div>
        <input type="text" name="keyword" id="keyword" value="${keyword}">
    </div>
    <button type="submit">검색</button>
</form>

<!-- 검색 결과 영역 -->
<div id="searchResults">
    <jsp:include page="searchResults.jsp"/>  <!-- ✅ 검색 결과만 포함 -->
</div>
