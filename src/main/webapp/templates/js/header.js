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

let modal = document.getElementById('image_modal');

document.getElementById('profile_modal').addEventListener('click', () => {
    if(modal.style.display === 'none') {
        modal.style.display = 'block';
        profileDiv.style.display = 'none';
    } else {
        modal.style.display = 'none';
    }
});

document.getElementById('modal_cancel').addEventListener('click', () => {
    modal.style.display = 'none';
});

document.getElementById('modal_image_cancel').addEventListener('click', () => {
    modal.style.display = 'none';
});

function showImage(input){
    if(input.files && input.files[0]) {
        let file = new FileReader();
        file.onload = (e) => {
            document.getElementById('profile_image').setAttribute('src', e.target.result);
        }
        file.readAsDataURL(input.files[0]);
    }
}

document.getElementById('modal_submit').addEventListener('click', () => {
    let xhttp = new XMLHttpRequest();
    let file = document.getElementById("ex_file");
    let formData = new FormData();

    formData.append('profile', file.files[0]);

    xhttp.open("PUT", `http://localhost:8080/user/profile/${USER.data.username}`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            // location.reload();
        }
    };
    xhttp.send(formData);
});