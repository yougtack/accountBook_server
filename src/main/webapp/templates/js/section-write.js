let writeView = document.getElementById('section_write');
let incomeView = document.getElementById('section_income');
let calendarView = document.getElementById('section_calendar');

(function reset() {
    writeView.style.display = 'block';
    incomeView.style.display = 'none';
    calendarView.style.display = 'none';
})();

document.getElementById('write_view').addEventListener('click', () => {
    writeView.style.display = 'block';
    incomeView.style.display = 'none';
    calendarView.style.display = 'none';
});

document.getElementById('income_view').addEventListener('click', () => {
    writeView.style.display = 'none';
    incomeView.style.display = 'block';
    calendarView.style.display = 'none';
});

document.getElementById('calendar_view').addEventListener('click', () => {
    writeView.style.display = 'none';
    incomeView.style.display = 'none';
    calendarView.style.display = 'block';
});

document.getElementById('write_submit').addEventListener('click', () => {
    let xhttp = new XMLHttpRequest();
    let writeForm = document.getElementById('write_form');

    let data = {
        user: {
            username: USER.data[0]
        },
        ab_write_date: writeForm.ab_write_date.value,
        ab_where_to_use: writeForm.AB_where_to_use.value,
        cash_cost: deleteComma(writeForm.cash_cost.value),
        card_cost: deleteComma(writeForm.card_cost.value),
        type: document.getElementById('Type').textContent
    }

    xhttp.open("POST", `http://localhost:8080/accountBook`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            location.reload();
        }
    };

    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send(JSON.stringify(data));
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
    values.innerHTML = `미분류`;
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

window.addEventListener('load', () => {
    let now = new Date();
    let today = document.getElementById('today');
    let day = new Array('일', '월', '화', '수', '목', '금', '토');
    let month = ((now.getMonth() + 1) <= 9) ? '0' + (now.getMonth() + 1) : (now.getMonth() + 1);
    let date = ((now.getDate() + 1) <= 9) ? '0' + now.getDate() : now.getDate();

    today.innerText = now.getFullYear() + "." + month + "." + date + ".(" + day[now.getDay()] + ")";

    document.getElementById('write_today').value = now.getFullYear() + "-" + month + "-" + date;
    document.getElementById('income_today').value = now.getFullYear() + "-" + month + "-" + date;
});

function alertTest() {
    // window.webkit.messageHandlers.(name).postMessage(message);
   alert("HI");
}