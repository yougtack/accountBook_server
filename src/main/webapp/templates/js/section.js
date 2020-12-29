let btnMoneyHide = document.getElementById('btn_money_hide');
let btnCount = 0;

btnMoneyHide.addEventListener('click', () => {
    let money = document.getElementsByClassName('money');
    let subMoney = document.getElementsByClassName('sub_money');

    (btnCount === 0) ? moneyHide() :  location.reload();

    btnMoneyHide.src = './image/btn_money_on.png';

    function moneyHide(){
        for (value of money){
            value.textContent = '********';
        }
        for (value of subMoney){
            value.textContent = '********';
        }
        ++btnCount;
    }
});

window.addEventListener('load', () => {
    let now = new Date();
    let today = document.getElementById('today');
    let day = new Array('일', '월', '화', '수', '목', '금', '토');

    today.innerText = now.getFullYear() + "." + (now.getMonth() + 1) + "." + now.getDate() + ".(" + day[now.getDay()] + ")";

    document.getElementById('write_today').value = now.getFullYear() + "." + (now.getMonth() + 1) + "." + now.getDate();
    document.getElementById('income_today').value = now.getFullYear() + "." + (now.getMonth() + 1) + "." + now.getDate();

});

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
    bottomBtn[2].style.borderBottom = '1px solid #908F8F';
    bottomBtn[2].style.fontWeight = '200';
});

bottomBtn[1].addEventListener('click', () => {
    bottomBtn[1].style.borderBottom = 'none';
    bottomBtn[1].style.fontWeight = '500';
    bottomBtn[0].style.borderBottom = '1px solid #908F8F';
    bottomBtn[0].style.fontWeight = '200';
    bottomBtn[2].style.borderBottom = '1px solid #908F8F';
    bottomBtn[2].style.fontWeight = '200';
});

bottomBtn[2].addEventListener('click', () => {
    bottomBtn[2].style.borderBottom = 'none';
    bottomBtn[2].style.fontWeight = '500';
    bottomBtn[0].style.borderBottom = '1px solid #908F8F';
    bottomBtn[0].style.fontWeight = '200';
    bottomBtn[1].style.borderBottom = '1px solid #908F8F';
    bottomBtn[1].style.fontWeight = '200';
});