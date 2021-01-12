let now = new Date();

let budgetWriteView = document.getElementById('budget_write_view'),
    budgetReportView = document.getElementById('budget_report_view');

(function reset() {
    budgetWriteView.style.display = 'block';
    budgetReportView.style.display = 'none';
})();

document.getElementById('budget_write').addEventListener('click', () => {
    budgetWriteView.style.display = 'block';
    budgetReportView.style.display = 'none';
});

document.getElementById('budget_report').addEventListener('click', () => {
    budgetWriteView.style.display = 'none';
    budgetReportView.style.display = 'block';
});

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
    let date = ((now.getDate() + 1) <= 9) ? '0' + now.getDate() : now.getDate();

    document.getElementById('budget_today').innerText =
        now.getFullYear() + "." + month + "." + date + ".(" + day[now.getDay()] + ")";

    document.getElementById('budget_first').innerText = (now.getMonth() + 1) + "-" + 1;
    document.getElementById('budget_last').innerText = (now.getMonth() + 1) + "-" + lastDay;
});

let budgetTr = document.getElementsByClassName('budget_tr');

(function budgetCost() {
    let budgetCost = 0, spendCost = 0, cost = 0;
    let budgetTotal = document.getElementById('budget_total');
    let spendTotal = document.getElementById('spend_total');
    let totalCost = document.getElementById('total');
    for(value of BUDGET_DATA.data){
        for (let i = 0; i < 13; i++){
            if (value.type === budgetTr[i].cells[0].innerText){
                (value.type === '미분류')
                    ? budgetTr[i].cells[1].children[0].textContent = '-'
                    : budgetTr[i].cells[1].children[0].textContent = value.budget.format();
                budgetTr[i].cells[2].children[0].textContent = value.spending.format();
                (value.total_cost < 0)
                    ? budgetTr[i].cells[3].style.color = '#ff5658'
                    : budgetTr[i].cells[3].style.color = 'black';
                budgetTr[i].cells[3].children[0].textContent = value.total_cost.format();
                budgetCost += value.budget;
                spendCost += value.spending;
                cost += value.total_cost;
            }
        }
    }
    budgetTotal.textContent = budgetCost.format();
    spendTotal.textContent = spendCost.format();
    (cost < 0 ) ? totalCost.style.color = '#ff5658' : totalCost.style.color = 'black';
    totalCost.textContent = cost.format();
})();

function SetComma(str) {
    str = str.replace(/,/g, '');
    let retValue = "";
    if (isNaN(str) == false) {
        for (let i = 1; i <= str.length; i++) {
            if (i > 1 && (i % 3) == 1)
                retValue = str.charAt(str.length - i)
                    + "," + retValue; else retValue = str.charAt(str.length - i) + retValue;
        }
    }
    return retValue;
}

const budgetCostInput = document.getElementsByClassName('budget_cost_input');

for(let i = 0; i < budgetCostInput.length; i++){
    budgetCostInput[i].addEventListener('click', () => {
        budgetCostInput[i].style.display = 'none';
        document.getElementsByClassName('input_list')[i].innerHTML =
            `<input class="budget_input font budget_cost_input" type="text"
                onkeyup="this.value = SetComma(this.value)" 
                onfocus="this.value = SetComma(this.value)"
                maxlength="13"
                value="${budgetTr[i].cells[1].children[0].textContent}"/>`;
    });
}

document.getElementById('budget_submit').addEventListener('click', () => {
    let xhttp = new XMLHttpRequest();
    let data = [];
    let month = ((now.getMonth() + 1) <= 9) ? '0' + (now.getMonth() + 1) : (now.getMonth() + 1);
    let date = ((now.getDate() + 1) <= 9) ? '0' + now.getDate() : now.getDate();

    for (value of budgetTr) {
        if (value.cells[1].children[0].value >= "0"
            && value.cells[1].children[0].value !== undefined) {
            data.push(
                {
                    username : USER.data[0],
                    insert_date : now.getFullYear() + "-" + month + "-" + date,
                    budget: deleteComma(value.cells[1].children[0].value),
                    budget_type: value.cells[0].textContent
                })
        }
    }
    xhttp.open("POST",
        "http://localhost:8080/accountBook/budget", false);
    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            location.href = "budget.html";
        }
    };
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send(JSON.stringify(data));
});