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