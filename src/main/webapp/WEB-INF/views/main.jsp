<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<h1>MOVIMENT</h1>
<p>영화를 위한 우리의 작은 움직임</p>
<div id="movie-list">
</div>
<script>
    const getTrendMovies = () => {
        fetch('/api/nowPlaying')
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
                console.log(data);
                const listElement = document.getElementById("movie-list");

                data.forEach(movie => {
                    const div = document.createElement("div");
                    div.classList.add("movie-card");
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

    getTrendMovies();
</script>