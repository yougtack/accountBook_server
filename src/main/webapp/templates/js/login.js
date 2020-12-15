function loginCheck() {
    let userName = document.getElementById('username');
    let passWord = document.getElementById('password');
    let inputLabel = document.getElementsByClassName('input_label');
    let loginForm = document.getElementById('login_form');

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
        loginForm.submit();
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