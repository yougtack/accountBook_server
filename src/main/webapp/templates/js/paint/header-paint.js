const USER = {
    data: []
}

let header_login_paint =
`<header class="header_wrapper">
    <div class="header_container">
        <img id="nav_icon" class="nav_icon" src="image/list_icon.png" alt="list" />
        <a class="header_logo font" href="write.html">가계부</a>
        <div class="profile_content">
            <img class="profile" src="image/info.gif" alt="profile" />
            <p class="profile_name font">현민</p>
        </div>
    </div>
</header>`;

let header_paint;


document.write(header_login_paint);

(function test() {
    let xhttp = new XMLHttpRequest();
    const URL = "http://localhost:8080";

    xhttp.open("get", URL + `/user`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            USER.data = JSON.parse(xhttp.responseText);
            console.log(USER.data);
        }
    };
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send();
})();