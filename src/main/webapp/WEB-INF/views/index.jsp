<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Moviment</title>
</head>
<body>
    <div>
        <form action="/login" method="post">
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
