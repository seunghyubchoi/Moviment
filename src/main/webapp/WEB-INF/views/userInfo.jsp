<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<h1>회원정보수정</h1>
<form action="/updateUserInfo" method="post">
    <div>
        <h5>EMAIL</h5>
        <input type="text" name="email" value="${sessionScope.user.email}" readonly>
    </div>
    <div>
        <h5>현재비밀번호</h5>
        <input type="password" name="currentPassword" required>
    </div>
    <div>
        <h5>변경비밀번호</h5>
        <input type="password" name="newPassword" required>
    </div>
    <div>
        <h5>비밀번호확인</h5>
        <input type="password" name="confirmPassword" required>
    </div>
    <div>
        <h5>이름</h5>
        <input type="text" name="username" value="${sessionScope.user.username}">
    </div>
    <button type="submit">변경</button>
</form>
