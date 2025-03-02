<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<html>
<head>
    <title>Moviment</title>
    <style>
        .form-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }
        .form-box {
            background: #fff;
            padding: 30px;
            border: 1px solid #ddd;
            border-radius: 12px;
            box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
            width: 350px;
            text-align: center;
        }
        .input-group {
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 10px;
        }
        .input-group label {
            font-weight: bold;
            min-width: 80px;
            text-align: left;
            margin-right: 10px;
        }
        .button-group {
            display: flex;
            justify-content: center;
            gap: 10px;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div class="container form-container">
        <div class="form-box">
            <h1>MOVIMENT</h1>
            <br>
            <form action="/register" method="post" id="registerForm">
                <div class="input-group">
                    <label class="form-label" for="email">E-mail</label>
                    <input class="form-control" type="text" id="email" name="email">
                </div>
                <div class="input-group">
                    <label class="form-label" for="password">비밀번호</label>
                    <input class="form-control" type="password" id="password" name="password">
                </div>
                <div class="input-group">
                    <label class="form-label" for="username">이름</label>
                    <input class="form-control" type="text" id="username" name="username">
                </div>
                <div class="button-group">
                    <button type="submit" class="btn btn-danger">회원가입</button>
                    <a href="/" class="btn btn-secondary">취소</a>
                </div>
            </form>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
    <script>
        let serverMessage = "${message}";
    </script>
</body>
</html>
