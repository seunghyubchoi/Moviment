<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>Title</title>
</head>
<script>
    let email = document.querySelector('input[name="email"]');
    let password = document.querySelector('input[name="password"]');
    let username = document.querySelector('input[name="username"]');

    if (email.trim() === "") {
        alert("이메일을 입력하세요.");
    }

    if (password.trim() === "") {
        alert("비밀번호를 입력하세요.");
    }

    if (username.trim() === "") {
        alert("아이디를 입력하세요.");
    }
</script>
<body>
    <form action="/register" method="post">
        <table>
            <tr>
                <th>E-mail</th>
                <td><input type="text" name="email" required></td>
            </tr>
            <tr>
                <th>비밀번호</th>
                <td><input type="password" name="password" required></td>
            </tr>
            <tr>
                <th>이름</th>
                <td><input type="text" name="username" required></td>
            </tr>
        </table>
        <button type="submit">회원가입</button>
    </form>
    <form action="/" method="get">
        <button type="submit">취소</button>
    </form>
</body>
</html>
