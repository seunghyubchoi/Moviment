<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="/register" method="post">
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
</body>
</html>
