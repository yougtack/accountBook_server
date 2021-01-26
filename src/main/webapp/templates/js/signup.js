function signupCheck() {
    let userName = document.getElementById('username');
    let email = document.getElementById('email');
    let passWord = document.getElementById('password');
    let signupLabel = document.getElementsByClassName('signup_label');
    let signupForm = document.getElementById('signup_form');

    let cnt = 0;

    (userName.value.trim().length <= 0) ? userNameLabel() : ++cnt;
    (email.value.trim().length <= 0) ? emailLabel() : ++cnt;
    (passWord.value.trim().length <= 0) ? passWordLabel() : ++cnt;

    function userNameLabel() {
        signupLabel[0].textContent = '유저이름을 입력해주세요.';
        signupLabel[0].style.color = 'red';
    }

    function emailLabel() {
        signupLabel[1].innerHTML = '이메일을 입력해주세요.';
        signupLabel[1].style.color = 'red';
    }

    function passWordLabel() {
        signupLabel[2].innerHTML = '비밀번호를 입력해주세요.';
        signupLabel[2].style.color = 'red'
    }

    if(cnt === 3){
        signupForm.submit();
        alert("회원가입 완료");
    }
}

document.getElementById('signup_btn').addEventListener('click', () => {
    signupCheck();
});

document.getElementById('password').addEventListener('keypress', (e) => {
    if(e.key === 'Enter'){
        signupCheck();
    }
});