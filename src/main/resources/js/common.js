// 서버에서 응답 받는 메세지
window.onload = function () {
    if (typeof serverMessage !== "undefined" && serverMessage.trim().length > 0 && serverMessage !== "null") {
        alert(serverMessage);
    }

    registerValidation(); // 회원가입 폼 검증
};

document.addEventListener("DOMContentLoaded", function() {
    loginValidation(); // 로그인 폼 검증
    //registerValidation(); // 회원가입 폼 검증
    onSelectMenu(); // 메뉴 클릭 시 AJAX로 content 변경
    onSearchMovie(); // 영화 검색 기능
    onSelectSearchMoviePageNumber(); // 영화 페이지 변경
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

// 메뉴 클릭 시 AJAX로 content 변경
function onSelectMenu() {
    document.querySelectorAll(".menu-link").forEach(menuItem => {
        menuItem.addEventListener("click", function(event) {
            event.preventDefault();

            document.querySelectorAll(".menu-link").forEach(item => item.classList.remove("active"));
            this.classList.add("active");

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

function onSearchMovie() {
    let searchForm = document.getElementById("searchForm");
    if(searchForm) {
        searchForm.addEventListener("submit", function (event) {
            event.preventDefault(); // 기본 폼 제출 방지

            let keyword = document.getElementById("keyword").value;
            fetch("/api/movies?keyword=" + encodeURIComponent(keyword))
                .then(response => {
                    if(response.ok) {
                        return response.text();
                    } else {
                        return response.json().then(result => {
                            return Promise.reject(result.message);
                        })
                    }
                })
                .then(data => {
                    document.getElementById("searchResults").innerHTML = data;
                    onSelectSearchMoviePageNumber();
                    onSelectSearchMovie(); // 영화 상세 검색 기능
                })
                .catch(error => alert(error));
        });
    }
}

function onSelectSearchMoviePageNumber() {
    let searchResults = document.getElementById("searchResults");
    if(searchResults) {
        document.querySelectorAll(".page-link").forEach(pageNumber => {
            pageNumber.addEventListener("click", function(event) {
                event.preventDefault();

                const keyword = this.getAttribute("data-content");
                const number = this.getAttribute("data-page");

                fetch("/api/search?keyword=" + encodeURIComponent(keyword) + "&page=" + number)
                    .then(response => response.text())
                    .then(data => {
                        document.getElementById("searchResults").innerHTML = data;
                        onSelectSearchMoviePageNumber();
                        onSelectSearchMovie(); // 영화 상세 검색 기능
                    })
                    .catch(error => console.error("AJAX 오류:", error));
            });
        });

    }
}

function onSelectSearchMovie() {
    let searchResults = document.getElementById("searchResults");
    if(searchResults) {
        document.querySelectorAll(".movie-card").forEach(movieCard => {
            movieCard.addEventListener("click", function(event) {
                event.preventDefault();

                const movieId = this.getAttribute("data-content");
                onLoadMovieInfo(movieId);
            });
        });
    }
}

function onLoadMovieInfo(movieId) {
    fetch(`/api/movies/${movieId}`)
        .then(response => response.text())
        .then(data => {
            document.getElementById("searchResults").innerHTML = data;
            onSelectSearchMoviePageNumber();
            addReview();
        })
        .catch(error => console.error("AJAX 오류:", error));
}

function addReview() {
    let addReview = document.getElementById("addReview");
    if(addReview) {
        addReview.addEventListener("submit", async function (event) {
            event.preventDefault(); // 기본 폼 제출 방지

            const movieId = document.getElementById("movieId").value;
            const reviewContent = document.getElementById("reviewContent").value;

            console.log(movieId);

            if (!reviewContent.trim()) {
                alert("댓글을 입력하세요.");
                return;
            }

            let response = await fetch("/api/addReview", {
                method: "POST"
                , headers: {
                    "Content-Type": "application/json"
                }
                , body: JSON.stringify({
                    movieId: movieId
                  , content: reviewContent
                })
            });

            if(response.ok) {
                document.getElementById("searchResults").value = ""; // 입력창 초기화
                onLoadMovieInfo(movieId);
            } else {
                alert("댓글 등록에 실패했습니다.")
            }
        });
    }
}