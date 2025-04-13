<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="container d-flex align-items-center justify-content-center py-5">
    <ul class="navbar-nav d-flex flex-row gap-5 align-items-end" style="column-gap: 3rem; cursor: pointer">
        <li class="nav-item">
            <a class="nav-link text-white fs-5 py-2" onclick="getMoviesInMain(this, 'nowPlaying')"><spring:message code="message.movie.nowPlaying" /></a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-white fs-5 py-2" onclick="getMoviesInMain(this, 'upComing')"><spring:message code="message.movie.upComing" /></a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-white fs-5 py-2" onclick="getMoviesInMain(this, 'topRated')"><spring:message code="message.movie.topRated" /></a>
        </li>
    </ul>
</div>



<div id="movie-list">
</div>

<div id="arrow-area">
</div>
<script>
    const listElement = document.getElementById("movie-list");
    const arrowBox = document.getElementById("arrow-area");

    // 언어 찾기
    const getSelectedLang = () => {
        return localStorage.getItem('lang') || 'ko';
    }

    const lang = getSelectedLang();
    console.log(`${lang}!!!!!`);
    // 하단 화살표 버튼
    const renderPage = (movieListType, page) => {
        arrowBox.innerHTML = "";
        if(!page || page === 1) {
            const nextBtn = document.createElement("button");
            nextBtn.className = "arrow";
            nextBtn.innerHTML = ">";
            nextBtn.onclick = () => {
               getMoviesInMain(movieListType, 2);
            }
            arrowBox.appendChild(nextBtn);
        } else {
            const prevBtn = document.createElement("button");
            prevBtn.className = "arrow";
            prevBtn.innerHTML = "<";
            prevBtn.onclick = () => {
                getMoviesInMain(movieListType, Number(page) - 1);
            }
            arrowBox.appendChild(prevBtn);

            const nextBtn = document.createElement("button");
            nextBtn.className = "arrow";
            nextBtn.innerHTML = ">";
            nextBtn.onclick = () => {
                getMoviesInMain(movieListType, Number(page) + 1);
            }
            arrowBox.appendChild(nextBtn);
        }
    }

    // 현재 상영작, 개봉예정작, 명예의전당
    const getMoviesInMain = (element, movieListType, page) => {

        // 메뉴 버튼 active
        document.querySelectorAll('.nav-link').forEach(link => {
            link.classList.remove('active');
        });

        if(element)
        {
            element.classList.add('active');
        }

        console.log(lang);

        fetch('/api/movies/main/' + movieListType + "?page=" + (page || 1) + "&lang=" + lang)
            .then(response => {
                if(!response.ok) { // 에러 발생 시
                    return response.text()
                        .then(errorMessage => {
                            throw new Error(errorMessage);
                        })

                }
                return response.json();
            })
            .then(data => {
                listElement.innerHTML = "";

                data.forEach(movie => {
                    const div = document.createElement("div");
                    const img = document.createElement("img");
                    img.src = "https://image.tmdb.org/t/p/w500" + movie.posterPath;
                    img.alt = movie.title;
                    
                    div.appendChild(img);
                    listElement.appendChild(div);
                })
            })
            .catch(error => {
                console.log(error);
                alert("데이터를 불러오는 데 실패했습니다." +
                    "잠시 후 다시 시도해주세요.");
            })
        renderPage(movieListType, page);
    }

    getMoviesInMain(null, "nowPlaying", 1);
</script>