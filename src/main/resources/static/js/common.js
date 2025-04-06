// 서버에서 응답 받는 메세지
window.onload = function () {
    if (typeof serverMessage !== "undefined" && serverMessage.trim().length > 0 && serverMessage !== "null") {
        alert(serverMessage);
    }

    registerValidation(); // 회원가입 폼 검증
};

window.addEventListener("popstate", function (event) {
    if (event.state) {
        loadContentPage(event.state.type, false); // AJAX로 이전 상태의 페이지 불러오기
    }
});


// DOM 로드 시 준비되는 함수들
document.addEventListener("DOMContentLoaded", function() {
    loginValidation(); // 로그인 폼 검증
});

// 로그인 폼 검증
function loginValidation() {
    let loginForm = document.getElementById("loginForm");
    if(loginForm) {
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
    }
}

// 회원가입 폼 검증
function registerValidation() {
    let registerForm = document.getElementById("registerForm");
    if(registerForm) {
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
                alert("이름을 입력하세요.");
                event.preventDefault();
            }
        });
    }
}

function onSearchBoard() {
    fetch("/board")
        .then(response => response.text())
        .then(html => {
            document.getElementById("content").innerHTML = html;
        })
        .catch(error => console.error("게시판 로드 오류"));
}

function addBoard() {
    let userId = document.getElementById("userId").value;
    let boardContent = document.getElementById("boardContent").value;
    fetch("/board", {
        method: "POST"
        , headers: {"Content-Type" : "application/json"}
        , body: JSON.stringify({ userid: userId, content: boardContent })
    })
}