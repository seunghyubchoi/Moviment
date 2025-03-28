<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<div class="text-center my-3">
    <h1 class="fw-bold text-dark">MOVIMENT</h1>
</div>

<div class="text-center mb-4">

    <div class="d-flex justify-content-center gap-3">
        <a href="/moviment/userInfo">회원정보수정</a>
        <a href="/moviment/favorites">즐겨찾기</a>
    </div>
</div>


<!-- 메뉴 리스트 -->
<ul class="nav nav-pills flex-column">
    <li class="nav-item">
        <a href="/moviment" class="nav-link menu-link" data-content="main">
            <i class="bi bi-house-door"></i> 홈
        </a>
    </li>
    <li class="nav-item">
        <a href="/moviment/search" class="nav-link menu-link" data-content="search">
            <i class="bi bi-search"></i> 검색
        </a>
    </li>
    <li class="nav-item">
        <a href="/moviment/board" class="nav-link menu-link" data-content="board">
            <i class="bi bi-chat-dots"></i> 게시판
        </a>
    </li>
</ul>