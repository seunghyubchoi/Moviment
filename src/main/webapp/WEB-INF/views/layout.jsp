<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Moviment</title>

    <script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
    <script>
        let serverMessage = "${message}";
    </script>

    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { display: flex; height: 100vh; }
        .menu { width: 20%; background: #f4f4f4; padding: 20px; }
        .content { width: 60%; padding: 20px; }
        .chat { width: 20%; background: #ddd; padding: 20px; }
    </style>
</head>
<body>

<!-- 좌측 메뉴 -->
<div class="menu">
    <jsp:include page="menu.jsp"/>
</div>

<!-- 메인 컨텐츠 -->
<div class="content" id="content">
    <jsp:include page="main.jsp"/>
</div>

<!-- 우측 채팅 -->
<div class="chat">
    <jsp:include page="chat.jsp"/>
</div>

<!-- footer -->
<div class="footer">
    <jsp:include page="footer.jsp"/>
</div>

</body>
</html>
