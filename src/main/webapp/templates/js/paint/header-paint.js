const USER = {
    data: []
};

(function user() {
    let xhttp = new XMLHttpRequest();
    const URL = "http://localhost:8080";

    xhttp.open("GET", URL + `/user`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            USER.data = xhttp.responseText.split(",");
        }
    };
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send();
})();

let signupPaint =
    `<header class="header_wrapper">
            <div class="header_container">
                <img id="nav_icon" class="nav_icon" src="image/list_icon.png" alt="list" />
                <a class="header_logo font" href="write.html">가계부</a>`;
if (USER.data.length === 1) {
    signupPaint +=`
        <div class="profile_content">
            <span class="header_login font" onclick="location.href = 'login.html'">로그인</span>
        </div>`;
    // alert("로그인 후 이용할 수 있는 페이지 입니다.");
    // location.href = 'login.html';
} else {
    signupPaint +=`
        <div class="profile_content">
            <img class="profile" src="image/info.gif" alt="profile"/>
            <div id="profile_div" class="profile_div">
                <img class="profile_image" src="image/info.gif" />
                <div class="info">
                    <span class="profile_text font">${USER.data[0]}</span>
                    <span class="profile_email font">${USER.data[1]}</span>
                    <span id="logout" class="logout font">로그아웃</span>
                </div>
            </div>
        </div>`;
}
signupPaint +=`
            </div>
    </header>`;

document.write(signupPaint);