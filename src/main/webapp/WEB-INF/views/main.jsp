<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<div class="container d-flex align-items-center justify-content-center">
    <ul class="navbar-nav d-flex flex-row gap-5 align-items-end" style="column-gap: 3rem; cursor: pointer">
        <li class="nav-item">
            <a class="nav-link text-white fs-5 py-2" onclick="getMoviesInMain('nowPlaying')">현재상영작</a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-white fs-5 py-2" onclick="getMoviesInMain('upComing')">개봉예정작</a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-white fs-5 py-2" onclick="getMoviesInMain('topRated')">명예의전당</a>
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
    const getMoviesInMain = (movieListType, page) => {
        console.log(movieListType);
        console.log(page);
        fetch('/api/movies/main/' + movieListType + (page ? "?page=" + page : ""))
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

    getMoviesInMain("nowPlaying");
</script>