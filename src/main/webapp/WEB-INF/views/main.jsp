<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<div class="container d-flex align-items-center justify-content-center">
    <ul class="navbar-nav d-flex flex-row gap-5 align-items-end" style="column-gap: 3rem;">
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

<a></a>
<div id="movie-list">
</div>
<script>
    const listElement = document.getElementById("movie-list");

    // 현재 상영작
    const getMoviesInMain = (e) => {
        fetch('/api/movies/main/' + e)
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
                alert(error);
            })
    }

    getMoviesInMain("nowPlaying");
</script>