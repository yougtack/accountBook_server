let now = new Date();

const BUDGET_DATA = {
    data : []
};

(function budget() {
    let xhttp = new XMLHttpRequest()
    let month = now.getMonth() + 1;
    (month <= 9) ? month = '0' + month : month;

    xhttp.open("GET",
        `http://localhost:8080/accountBook/budget/${USER.data[0]}/${now.getFullYear()}-${month}`, false);
    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            BUDGET_DATA.data = JSON.parse(xhttp.responseText);
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
    let month = ((now.getMonth() + 1) <= 9) ? '0' + (now.getMonth() + 1) : (now.getMonth() + 1);

    document.getElementById('budget_today').innerText =
        now.getFullYear() + "." + month + ".0" + now.getDate() + ".(" + day[now.getDay()] + ")";

    document.getElementById('budget_first').innerText = (now.getMonth() + 1) + "-" + 1;
    document.getElementById('budget_last').innerText = (now.getMonth() + 1) + "-" + lastDay;
});

(function test() {
    let budgetTr = document.getElementsByClassName('budget_tr');
    console.log(BUDGET_DATA.data);
    for(value of BUDGET_DATA.data){
        for (let i = 0; i < 12; i++){
            if (value.type === budgetTr[i].cells[0].innerText){
                budgetTr[i].cells[1].textContent = value.budget.format();
                budgetTr[i].cells[2].textContent = value.spending.format();
                budgetTr[i].cells[3].textContent = value.total_cost.format();
            }
        }
    }
})();