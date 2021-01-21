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
            USER.data = JSON.parse(xhttp.responseText);
            console.log(USER.data);
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
if (USER.data.username === null) {
    signupPaint +=`
        <div class="profile_content">
            <span class="header_login font" onclick="location.href = 'login.html'">로그인</span>
        </div>`;
    // alert("로그인 후 이용할 수 있는 페이지 입니다.");
    // location.href = 'login.html';
} else {
    signupPaint +=`
        <div class="profile_content">
            <img class="profile" src="../../..${USER.data.profile_path}" alt="profile"/>
            <div id="profile_div" class="profile_div">              
                <div class="profile_frame">
                    <img class="profile_image" src="${USER.data.profile_path}"/>
                    <span class="profile_change_text font">프로필 변경</span>
                </div>
                <div class="info">
                    <span class="profile_text font">${USER.data.username}</span>
                    <span class="profile_email font">${USER.data.email}</span>
                    <span id="logout" class="logout font">로그아웃</span>
                </div>
            </div>
        </div>`;
}
signupPaint +=`
            </div>
    </header>`;

document.write(signupPaint);