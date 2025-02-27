// 서버에서 응답 받는 메세지
window.onload = function () {
    if (typeof serverMessage !== "undefined" && serverMessage.trim().length > 0 && serverMessage !== "null") {
        alert(serverMessage);
    }
};

document.addEventListener("DOMContentLoaded", function() {
    loginValidation(); // 로그인 폼 검증
    onSelectMenu(); // 메뉴 클릭 시 AJAX로 content 변경
    onSearchMovie(); // 영화 검색 기능
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

                    // AJAX로 페이지 로드한 경우, 이벤트 리스너 재등록
                    if(contentPage === "search") {
                        onSearchMovie();
                    }

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

function onSearchMovie() {
    let searchForm = document.getElementById("searchForm");
    if(searchForm) {
        searchForm.addEventListener("submit", function (event) {
            event.preventDefault(); // 기본 폼 제출 방지

            let keyword = document.getElementById("keyword").value;
            fetch("/api/search?keyword=" + encodeURIComponent(keyword))
                .then(response => {
                    return response.json().then(result => {
                        if(!response.ok) {
                            return Promise.reject(result.message);
                        } else {
                            return result;
                        }
                    })
                })
                .then(data => {
                    let searchResult = document.getElementById("searchResults");
                    searchResult.innerHTML = ""; // 기존 내용 초기화

                    console.log("data");
                    console.log(data);

                    if(data && data.length > 0) {

                        let resultHtml = "<ul>";
                        data.forEach(movie => {
                            resultHtml += `<li>${movie.title}</li>`;
                        });
                        resultHtml += "</ul>";
                        searchResult.innerHTML = resultHtml;
                    }
                })
                .catch(error => alert(error));
        });
    }
}