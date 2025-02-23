<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Moviment</title>
</head>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        let loginForm = document.getElementById("loginForm");
        loginForm.addEventListener("submit", function(event){

            let email = document.querySelector('input[name="email"]');
            let password = document.querySelector('input[name="password"]');

            if (email.value.trim() === "") {
                alert("이메일을 입력하세요.");
                event.preventDefault();
            } else if (password.value.trim() === "") {
                alert("비밀번호를 입력하세요.");
                event.preventDefault();
            }
        });
    });
</script>
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
    <script>
        window.onload = function () {
            var msg = `${message}`;

            if (msg && msg.trim().length > 0 && msg !== "null") {
                alert(msg);
            }
        };
    </script>
</body>
</html>
