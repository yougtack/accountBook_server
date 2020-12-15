function login(username, password) {
    let xhttp = new XMLHttpRequest();
    const URL = "http://localhost:8080";
    let data = {
        username: username,
        password: password
    }

    xhttp.open("POST", URL + `/login`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            USER.data = JSON.parse(xhttp.responseText);
            console.log(USER.data);
        }
    };
    console.log(data);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send(JSON.stringify(data));
};

function loginCheck() {
    let userName = document.getElementById('username');
    let passWord = document.getElementById('password');
    let inputLabel = document.getElementsByClassName('input_label');

    if(userName.value.trim().length <= 0 && passWord.value.trim().length <=0) {
        inputLabel[0].textContent = '아이디를 입력해주세요.';
        inputLabel[0].style.color = 'red';

        inputLabel[1].innerHTML = '비밀번호를 입력해주세요.';
        inputLabel[1].style.color = 'red';
    } else if(userName.value.trim().length <= 0) {
        inputLabel[0].textContent = '아이디를 입력해주세요.';
        inputLabel[0].style.color = 'red';
    } else if (passWord.value.trim().length <=0){
        inputLabel[1].innerHTML = '비밀번호를 입력해주세요.';
        inputLabel[1].style.color = 'red';
    } else {
        login(userName.value, passWord.value);
    }
}

document.getElementById('login_btn').addEventListener('click', () => {
    loginCheck();
})

document.getElementById('password').addEventListener('keypress', (e) => {
    if(e.key === 'Enter'){
        loginCheck();
    }
});