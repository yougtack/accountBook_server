let now = new Date();

(function budget() {
    let xhttp = new XMLHttpRequest();

    xhttp.open("GET",
        `http://localhost:8080/accountBook/budget/${USER.data[0]}/${now.getFullYear()}-${(now.getMonth() + 1)}`, false);
    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            console.log(JSON.parse(xhttp.responseText));
        }
    };

    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send();
})();

let bottomBtn = document.getElementsByClassName('bottom_btn');

window.addEventListener('load', () => {
    bottomBtn[0].style.borderBottom = 'none';
    bottomBtn[0].style.fontWeight = '500';
});

bottomBtn[0].addEventListener('click', () => {
    bottomBtn[0].style.borderBottom = 'none';
    bottomBtn[0].style.fontWeight = '500';
    bottomBtn[1].style.borderBottom = '1px solid #908F8F';
    bottomBtn[1].style.fontWeight = '200';
});

bottomBtn[1].addEventListener('click', () => {
    bottomBtn[1].style.borderBottom = 'none';
    bottomBtn[1].style.fontWeight = '500';
    bottomBtn[0].style.borderBottom = '1px solid #908F8F';
    bottomBtn[0].style.fontWeight = '200';
});

window.addEventListener('load', () => {
    let day = new Array('일', '월', '화', '수', '목', '금', '토');
    let lastDay = ( new Date( now.getFullYear(), now.getMonth(), 0) ).getDate();

    document.getElementById('budget_today').innerText =
        now.getFullYear() + "." + (now.getMonth() + 1) + "." + now.getDate() + ".(" + day[now.getDay()] + ")";

    document.getElementById('budget_first').innerText = (now.getMonth() + 1) + "-" + 1;
    document.getElementById('budget_last').innerText = (now.getMonth() + 1) + "-" + (lastDay + 1);
});