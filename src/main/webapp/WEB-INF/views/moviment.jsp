<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<head>
    <title>Moviment</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/common.js"></script>
</head>
<script>
    let serverMessage = "${message}";
</script>
<body class="bg-black bg-opacity-10 min-vh-100 d-flex flex-column">

<!-- 상단 메뉴바 -->
<nav class="navbar navbar-expand-lg bg-dark navbar-dark">
    <div class="container d-flex align-items-center">
        <a class="navbar-brand fw-bold fs-3 text-white me-4" href="/moviment">MOVIMENT</a>

        <ul class="navbar-nav d-flex flex-row gap-3 align-items-end">
            <li class="nav-item">
                <a class="nav-link text-white fs-5 py-2" href="/moviment/search"><spring:message code="message.movie.search"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white fs-5 py-2" href="/moviment/board"><spring:message code="message.movie.board"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white fs-5 py-2" href="/moviment/userInfo"><spring:message code="message.movie.profile"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white fs-5 py-2" href="/logout"><spring:message code="message.movie.logout"/></a>
            </li>
        </ul>
    </div>
</nav>

<!-- 중앙 콘텐츠 -->
<div class="flex-grow-1 bg-dark d-flex justify-content-center align-items-center">
    <div class="bg-white rounded-4 shadow p-3" style="min-width: 900px; max-width: 1000px; width: 100%; min-height: 700px;">
        <section id="content">
            <jsp:include page="${contentPage}"/>
        </section>
    </div>
</div>

</body>
</html>
