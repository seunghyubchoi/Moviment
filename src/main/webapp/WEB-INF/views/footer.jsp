<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<footer>
    <p>© 2025 Moviment</p>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            // 메뉴 클릭 시 AJAX로 content 변경
            document.querySelectorAll(".menu-link").forEach(menuItem => {
                menuItem.addEventListener("click", function(event) {
                    event.preventDefault();

                    const contentPage = this.getAttribute("data-content");
                    fetch("/loadContent?content=" + contentPage)
                        .then(response => response.text())
                        .then(html => {
                            document.getElementById("content").innerHTML = html;

                            initContentPage(contentPage);
                        })
                        .catch(error => console.error("AJAX 오류:", error));
                });
            });
        });


        function initContentPage(contentPage) {
            console.log(contentPage);
            fetch("/api/" + contentPage)
                .then(response => {
                    if(!response.ok) {
                        alert("!!");
                    } else {
                        console.log(response.json());
                    }
                })
        }
    </script>
</footer>
