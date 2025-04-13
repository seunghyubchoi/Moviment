<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<html>
<head>
    <title>Moviment</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <meta charset="UTF-8">
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
                            <spring:message code="message.login.inputEmail" var="inputEmail" />
                            <label for="email" class="form-label">E-mail</label>
                            <input type="text" id="email" name="email" class="form-control" placeholder="${inputEmail}">
                        </div>

                        <div class="mb-3">
                            <spring:message code="message.login.inputPassword" var="inputPassword" />
                            <label for="password" class="form-label"><spring:message code="message.login.register" /></label>
                            <input type="password" id="password" name="password" class="form-control" placeholder="${inputPassword}" />
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-dark"><spring:message code="message.login.loginBtn"/></button>
                            <a href="/register" class="btn btn-outline-secondary"><spring:message code="message.login.register" /></a>
                        </div>
                    </form>
                    <br>
                    <div>
                        <a href="?lang=en" class="btn btn-dark">
                            en
                        </a>
                        &nbsp;
                        <a href="?lang=ko" class="btn btn-dark">
                            ko
                        </a>
                    </div>
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
