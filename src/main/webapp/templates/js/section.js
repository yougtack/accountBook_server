let btnMoneyHide = document.getElementById('btn_money_hide');
let btnCount = 0;

btnMoneyHide.addEventListener('click', () => {
    let money = document.getElementsByClassName('money');
    let subMoney = document.getElementsByClassName('sub_money');
    let footerSpend = document.getElementById('footer_spend');
    let footerBudget = document.getElementById('footer_budget');

    (btnCount === 0) ? moneyHide() :  location.reload();

    btnMoneyHide.src = './image/btn_money_on.png';

    function moneyHide(){
        for (value of money){
            value.textContent = '********';
        }
        for (value of subMoney){
            value.textContent = '********';
        }

        footerSpend.textContent = '********';
        footerBudget.textContent = '********';

        ++btnCount;
    }
});

let thisMonth = document.getElementById('this_month');

window.addEventListener('load', () => {
    let now = new Date();

    let lastDay = ( new Date( now.getFullYear(), now.getMonth(), 0) ).getDate();
    let month = ((now.getMonth() + 1) <= 9) ? '0' + (now.getMonth() + 1) : (now.getMonth() + 1);

    thisMonth.textContent = now.getFullYear() + "." + month + ".0" + 1 + " - "
        + now.getFullYear() + "." + month + "." + lastDay;
});

function deleteComma(str) {
    let num = parseInt(str.replace(/,/g,""))

    return num;
}