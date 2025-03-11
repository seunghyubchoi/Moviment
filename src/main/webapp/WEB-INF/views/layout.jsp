<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Moviment</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/common.js"></script>
</head>
<body class="d-flex flex-column vh-100">
<div class="container-fluid h-100">
    <div class="row h-100">
        <!-- 좌측 메뉴 -->
        <aside class="col-md-2 bg-light p-3">
            <jsp:include page="menu.jsp"/>
        </aside>

        <!-- 메인 컨텐츠 -->
        <main class="col-md-8 p-3" id="content">
            <jsp:include page="${contentPage}"/>
        </main>
    </div>
</div>

</body>
</html>
