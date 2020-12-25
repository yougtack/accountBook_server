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

    const test2 = document.getElementById('test_today');
    test2.value = now.getFullYear() + "." + (now.getMonth() + 1) + "." + now.getDate();

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

function SetComma(str) {
    str = str.replace(/,/g, '');
    let retValue = "";
    if (isNaN(str) == false) {
        for (let i = 1; i <= str.length; i++) {
            if (i > 1 && (i % 3) == 1) retValue = str.charAt(str.length - i) + "," + retValue; else retValue = str.charAt(str.length - i) + retValue;
        }
    }
    return retValue;
}

const body = document.querySelector('body');
const select = document.querySelector('.select');
const values = select.querySelector('.selected-option');
const option = select.querySelector('ul');
const opts = option.querySelectorAll('span');
const selectTarget = document.getElementsByClassName('main_class');
const selectSubTarget = document.getElementsByClassName('sub_class_frame');

/* 셀렉트영역 클릭 시 옵션 숨기기, 보이기 */
function selects(e){
    e.stopPropagation();
    option.setAttribute('style', `top:${select.offsetHeight}px`)
    if (option.classList.contains('hide')) {
        option.classList.remove('hide');
        option.classList.add('show');
    } else {
        option.classList.add('hide');
        option.classList.remove('show');
    }
    selectOpt();
}

/* 옵션선택 */
function selectOpt(){
    opts.forEach(opt=>{
        const innerValue = opt.innerHTML;
        function changeValue(){
            values.innerHTML = innerValue;
            for(value of selectSubTarget){
                value.style.display = 'none';
            }
        }
        opt.addEventListener('click',changeValue);
    });
}

/* 렌더링 시 옵션의 첫번째 항목 기본 선택 */
function selectFirst(){
    const firstValue = opts[0].innerHTML;
    values.innerHTML = `${firstValue}`
}

/* 옵션밖의 영역(=바디) 클릭 시 옵션 숨김 */
function hideSelect(){
    if(option.classList.contains('show')){
        option.classList.add('hide');
        option.classList.remove('show');
        for(value of selectSubTarget){
            value.style.display = 'none';
        }
    }
}

selectFirst();
select.addEventListener('click',selects);
body.addEventListener('click',hideSelect);

for(let i = 0; i < selectTarget.length; i++) {
    selectTarget[i].addEventListener('mouseover', () => {
        for (let j = 0; j < selectSubTarget.length; j++){
            if (i === j){
                continue;
            }
            selectSubTarget[j].style.display = 'none';
        }
        selectSubTarget[i].style.display = 'block';
    });
}