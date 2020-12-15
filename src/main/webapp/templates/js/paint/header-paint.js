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

// let loginPaint =
//     `<header class="header_wrapper">
//             <div class="header_container">
//                 <img id="nav_icon" class="nav_icon" src="image/list_icon.png" alt="list" />
//                 <a class="header_logo font" href="write.html">가계부</a>
//                 <div class="profile_content">
//                     <img class="profile" src="image/info.gif" alt="profile" />
//                     <p class="profile_name font">${USER.data.email}</p>
//                 </div>
//             </div>
//     </header>`;

let signupPaint =
    `<header class="header_wrapper">
            <div class="header_container">
                <img id="nav_icon" class="nav_icon" src="image/list_icon.png" alt="list" />
                <a class="header_logo font" href="write.html">가계부</a>
                <div class="profile_content">
                    <span class="header_login font" onclick="location.href = 'login.html'">로그인</span>
                </div>
            </div>
    </header>`;
document.write(signupPaint);

// (USER.data !== null) ? document.write(loginPaint) : document.write(signupPaint);