<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<div class="text-center my-3">
    <h1 class="fw-bold text-dark">MOVIMENT</h1>
</div>

<div class="text-center mb-4">
    <h5 class="text-dark">
        <i class="bi bi-person-circle"></i> ${sessionScope.userName}님 환영합니다!
    </h5>
</div>

<!-- 메뉴 리스트 -->
<ul class="nav nav-pills flex-column">
    <li class="nav-item">
        <a href="#" class="nav-link active menu-link text-dark" data-content="main">
            <i class="bi bi-house-door"></i> 홈
        </a>
    </li>
    <li class="nav-item">
        <a href="#" class="nav-link menu-link text-dark" data-content="search">
            <i class="bi bi-search"></i> 검색
        </a>
    </li>
    <li class="nav-item">
        <a href="#" class="nav-link menu-link text-dark" data-content="board">
            <i class="bi bi-chat-dots"></i> 게시판
        </a>
    </li>
</ul>