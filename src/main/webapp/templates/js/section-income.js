document.getElementById('income_submit').addEventListener('click', () => {
    let xhttp = new XMLHttpRequest();
    let incomeForm = document.getElementById('income_form');

    let data = {
        username: USER.data[0],
        income_date: incomeForm.income_today.value,
        income_where_to_get: incomeForm.income_where_to_get.value,
        income_cost: deleteComma(incomeForm.income_cost.value),
        income_type: document.getElementById('income_type').textContent
    }

    xhttp.open("POST", `http://localhost:8080/accountBook/income`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            location.reload();
        }
    };

    xhttp.setRequestHeader("Content-Type", "application/json");
    console.log(JSON.stringify(data));
    xhttp.send(JSON.stringify(data));
});

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
    values_income.innerHTML = `미분류`;
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