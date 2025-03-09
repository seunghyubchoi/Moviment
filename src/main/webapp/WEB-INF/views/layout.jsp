<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Moviment</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        html {
            height: 100%;
        }
        .menu-link.active {
            background-color: black !important;
            color: white !important;
        }
        /* ✅ 기본 footer 스타일 */
        footer {
            background-color: #222;
            color: white;
            text-align: center;
            padding: 15px;
            width: 100%;
        }
    </style>
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
            <jsp:include page="main.jsp"/>
        </main>

        <!-- 우측 채팅 -->
        <aside class="col-md-2 bg-secondary text-white p-3">
            <jsp:include page="chat.jsp"/>
        </aside>
    </div>
</div>

<!-- footer -->
<%--
<footer class="bg-dark text-white text-center py-3">
    <jsp:include page="footer.jsp"/>
</footer>
--%>

</body>
</html>
