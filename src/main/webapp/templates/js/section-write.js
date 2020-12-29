let writeView = document.getElementById('section_write');
let incomeView = document.getElementById('section_income');

(function reset() {
    writeView.style.display = 'block';
    incomeView.style.display = 'none';
})();

document.getElementById('write_view').addEventListener('click', () => {
    writeView.style.display = 'block';
    incomeView.style.display = 'none';
});

document.getElementById('income_view').addEventListener('click', () => {
    writeView.style.display = 'none';
    incomeView.style.display = 'block';
});

document.getElementById('write_submit').addEventListener('click', () => {
    let xhttp = new XMLHttpRequest();
    let writeForm = document.getElementById('write_form');

    function deleteComma(str) {
        let num = parseInt(str.replace(/,/g,""))

        return num;
    }

    let data = {
        username: USER.data[0],
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

const select_income = document.querySelector('.select_income');
const values_income = select_income.querySelector('.selected-option-income');
const option_income = select_income.querySelector('ul');
const opts_income = option_income.querySelectorAll('span');
const selectTarget_income = document.getElementsByClassName('income_main');
const selectSubTarget_income = document.getElementsByClassName('sub_income_frame');

/* 셀렉트영역 클릭 시 옵션 숨기기, 보이기 */
function selectsIncome(e){
    e.stopPropagation();
    option_income.setAttribute('style', `top:${select_income.offsetHeight}px`)
    if (option_income.classList.contains('hide')) {
        option_income.classList.remove('hide');
        option_income.classList.add('show');
    } else {
        option_income.classList.add('hide');
        option_income.classList.remove('show');
    }
    selectOptIncome();
}

/* 옵션선택 */
function selectOptIncome(){
    opts_income.forEach(opt=>{
        const innerValue = opt.innerHTML;
        function changeValue(){
            values_income.innerHTML = innerValue;
            for(value of selectSubTarget_income){
                value.style.display = 'none';
            }
        }
        opt.addEventListener('click',changeValue);
    });
}

/* 렌더링 시 옵션의 첫번째 항목 기본 선택 */
function selectFirstIncome(){
    const firstValue = opts[0].innerHTML;
    values_income.innerHTML = `${firstValue}`
}

/* 옵션밖의 영역(=바디) 클릭 시 옵션 숨김 */
function hideSelectIncome(){
    if(option_income.classList.contains('show')){
        option_income.classList.add('hide');
        option_income.classList.remove('show');
        for(value of selectSubTarget_income){
            value.style.display = 'none';
        }
    }
}

selectFirstIncome();
select_income.addEventListener('click',selectsIncome);
body.addEventListener('click',hideSelectIncome);

for(let i = 0; i < selectTarget_income.length; i++) {
    selectTarget_income[i].addEventListener('mouseover', () => {
        for (let j = 0; j < selectSubTarget_income.length; j++){
            if (i === j){
                continue;
            }
            selectSubTarget_income[j].style.display = 'none';
        }
        selectSubTarget_income[i].style.display = 'block';
    });
}