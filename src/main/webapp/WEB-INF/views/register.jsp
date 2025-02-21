<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>Title</title>
</head>
<script>

    document.addEventListener("DOMContentLoaded", function() {
       let registerForm = document.getElementById("registerForm");
        registerForm.addEventListener("submit", function(event){

           let email = document.querySelector('input[name="email"]');
           let password = document.querySelector('input[name="password"]');
           let username = document.querySelector('input[name="username"]');

           if (email.value.trim() === "") {
               alert("이메일을 입력하세요.");
               event.preventDefault();
           } else if (password.value.trim() === "") {
               alert("비밀번호를 입력하세요.");
               event.preventDefault();
           } else if (username.value.trim() === "") {
               alert("아이디를 입력하세요.");
               event.preventDefault();
           }
       });
    });

</script>
<body>
    <form action="/register" method="post" id="registerForm">
        <table>
            <tr>
                <th>E-mail</th>
                <td><input type="text" name="email"></td>
            </tr>
            <tr>
                <th>비밀번호</th>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <th>이름</th>
                <td><input type="text" name="username"></td>
            </tr>
        </table>
        <button type="submit">회원가입</button>
    </form>
    <form action="/" method="get">
        <button type="submit">취소</button>
    </form>

    <c:if test="${not empty errorMessage}">
        <script>
            alert("${errorMessage}");
        </script>
    </c:if>
</body>
</html>
