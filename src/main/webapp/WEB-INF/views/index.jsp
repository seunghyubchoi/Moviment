<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Moviment</title>
    <script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
    <script>
        let serverMessage = "${message}";
    </script>
</head>
<body>
    <div>
        <form action="/login" method="post" id="loginForm">
            <div>
                <label for="email">E-mail</label>
                <input type="text" id="email" name="email">
            </div>
            <div>
                <label for="password">비밀번호</label>
                <input type="password" id="password" name="password">
            </div>
            <button type="submit">로그인</button>
        </form>
        <form action="/register" method="get">
            <button type="submit">회원가입</button>
        </form>
    </div>
</body>
</html>
