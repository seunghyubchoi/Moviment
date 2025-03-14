<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<html>
<head>
    <title>Moviment</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="d-flex justify-content-center align-items-center vh-100 bg-light">

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card shadow p-4">
                <div class="card-body text-center">
                    <h1 class="fw-bold text-dark">MOVIMENT</h1>
                    <br>
                    <form action="/login" method="post" id="loginForm">
                        <div class="mb-3">
                            <label for="email" class="form-label">E-mail</label>
                            <input type="text" id="email" name="email" class="form-control" placeholder="이메일 입력">
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">비밀번호</label>
                            <input type="password" id="password" name="password" class="form-control" placeholder="비밀번호 입력">
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-dark">로그인</button>
                            <a href="/register" class="btn btn-outline-secondary">회원가입</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS & Custom JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script>
    let serverMessage = "${message}";
</script>

</body>
</html>
