// 서버에서 응답 받는 메세지
window.onload = function () {
    if (typeof serverMessage !== "undefined" && serverMessage.trim().length > 0 && serverMessage !== "null") {
        alert(serverMessage);
    }
};

document.addEventListener("DOMContentLoaded", function() {
    loginValidation(); // 로그인 폼 검증
    onSelectMenu(); // 메뉴 클릭 시 AJAX로 content 변경
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

// 메뉴 클릭 시 AJAX로 content 변경
function onSelectMenu() {
    document.querySelectorAll(".menu-link").forEach(menuItem => {
        menuItem.addEventListener("click", function(event) {
            event.preventDefault();

            const contentPage = this.getAttribute("data-content");
            fetch("/loadContent?content=" + contentPage)
                .then(response => response.text())
                .then(html => {
                    document.getElementById("content").innerHTML = html;

                    // 메뉴 선택 시 바로 실행할 어떤 것
                    //initContentPage(contentPage);
                })
                .catch(error => console.error("AJAX 오류:", error));
        });
    });
}

// 메뉴 실행 시 자동 실행할 함수
function initContentPage(contentPage) {
    console.log(contentPage);
    fetch("/api/" + contentPage)
        .then(response => {
            if(!response.ok) {
                alert("받은 것 없음");
            } else {
                console.log(response.json());
            }
        })
}