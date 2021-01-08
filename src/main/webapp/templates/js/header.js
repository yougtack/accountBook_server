const NAV_ICON = document.getElementById('nav_icon');
const NAV = document.getElementById('nav');

let nav_state = 'no';

NAV_ICON.addEventListener('click', () => {

    if(nav_state === 'no'){
        NAV.style.cssText= 'margin-left: 0px; transition: 1s; position: absolute;';
        nav_state = 'yes';
    } else {
        NAV.style.cssText= 'margin-left: -220px; transition: 1s; position: absolute;';
        nav_state = 'no';
    }
});

let profileDiv = document.getElementById('profile_div');

document.getElementsByClassName('profile')[0].addEventListener('click', () => {
    (profileDiv.style.display === 'none') ? profileDiv.style.display = 'block' : profileDiv.style.display = 'none';
});

document.getElementById('logout').addEventListener('click', () => {
    let xhttp = new XMLHttpRequest();

    xhttp.open("GET", `http://localhost:8080/user/logout`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            location.href = 'write.html';
        }
    };
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send();
});