const MONTH_MONEY = {
    data: [],
    spendingMonth : [],
    cumulative: []
};

Number.prototype.format = function(){
    if(this==0) return 0;

    let reg = /(^[+-]?\d+)(\d{3})/;
    let n = (this + '');

    while (reg.test(n)) n = n.replace(reg, '$1' + ',' + '$2');

    return n;
};

(function accountBook() {
    let xhttp = new XMLHttpRequest();
    const URL = "http://localhost:8080";

    let data = {
        username:USER.data[0]
    }

    xhttp.open("GET", URL + `/accountBook/spending/${USER.data[0]}`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            MONTH_MONEY.data = (JSON.parse(xhttp.responseText));
        }
    };

    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send();
})();

(function spendingMonth() {
    let xhttp = new XMLHttpRequest();
    const URL = "http://localhost:8080";

    let data = {
        username:USER.data[0]
    }

    xhttp.open("GET", URL + `/accountBook/spending_month/${USER.data[0]}`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            MONTH_MONEY.spendingMonth = JSON.parse(xhttp.responseText);
        }
    };

    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send();
})();

(function cumulative() {
    let xhttp = new XMLHttpRequest();

    let data = {
        username: USER.data[0]
    }

    xhttp.open("GET", `http://localhost:8080/accountBook/cumulative/${USER.data[0]}`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            MONTH_MONEY.cumulative = JSON.parse(xhttp.responseText);
        }
    };

    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send();
})();

/* percent */
let percentTotal = MONTH_MONEY.spendingMonth.insurance + MONTH_MONEY.spendingMonth.spending;
let percentSpending = MONTH_MONEY.spendingMonth.spending;
let savingPercent = (!!Math.round(MONTH_MONEY.spendingMonth.insurance / percentTotal * 100))
    ? Math.round(MONTH_MONEY.spendingMonth.insurance / percentTotal * 100) : 0;
let spendPercent = (!!Math.round(MONTH_MONEY.spendingMonth.spending / percentTotal * 100))
    ? Math.round(MONTH_MONEY.spendingMonth.spending / percentTotal * 100) : 0;
/* percent */

let navPaint =
`<nav id="nav" class="nav_wrapper">
    <div class="nav_container">
        <div class="nav_list nav_line">
            <ul>
                <li class="list_target" onclick="location.href='write.html'">
                    <div class="list_content">
                        <img class="list_icon" src="image/write_icon.png" alt="list_icon" />
                        <p class="list_text font">쓰기</p>
                    </div>
                </li>
                <li class="list_target" style="padding-left: 9px;" onclick="location.href='report.html'">
                    <div class="list_content">
                        <img class="list_icon" src="image/report_icon.png" alt="list_icon" />
                        <p class="list_text font">보고서</p>
                    </div>
                </li>
                <li class="list_target" onclick="location.href='budget.html'">
                    <div class="list_content">
                        <img class="list_icon" src="image/budget_icon.png" alt="list_icon" />
                        <p class="list_text font">예산쓰기</p>
                    </div>
                </li>
                <li class="list_target" onclick="location.href='story.html'">
                    <div class="list_content">
                        <img class="list_icon" src="image/hope_icon.png" alt="list_icon" />
                        <p class="list_text font">희망목표/이야기</p>
                    </div>
                </li>
            </ul>
        </div>
        <div class="nav_frame nav_line">
            <div class="nav_content">
                <img id="month_target" class="arrow_target" src="image/arrow/arrow_down.png" alt="down" />
                <h3 class="month_text font">
                    이달의 가계
                    <p class="month_day font">
                        <span id="first_date"></span>
                        <span id="last_date"></span>
                    </p>
                </h3>
            </div>
            <ul class="month_money nav_space">
                <li class="income">
                    <span class="symbol font">+</span>
                    <span class="money_text font">수입</span>
                    <span class="money font">${MONTH_MONEY.data.incomeThisMonth.format()}</span>
                </li>
                <li class="sub_income">
                    <span class="month_arrow font">└</span>
                    <span class="money_text font">이달의 수입</span>
                    <span class="sub_money font">${MONTH_MONEY.data.income_type.income.format()}</span>
                </li>
                <li class="sub_income">
                    <span class="month_arrow font">└</span>
                    <span class="money_text font">전월이월</span>
                    <span class="sub_money font">${MONTH_MONEY.data.income_type.income_last_month.format()}</span>
                </li>
                <li class="spend font">
                    <span class="symbol minus font">-</span>
                    <span class="money_text font">지출</span>
                    <span class="money font">${MONTH_MONEY.data.expenditure.format()}</span>
                </li>
                <li class="sub_income">
                    <span class="month_arrow font">└</span>
                    <span class="money_text font">현금지출</span>
                    <span class="sub_money font">${MONTH_MONEY.data.expenditure_type.expenditure_cash.format()}</span>
                </li>
                <li class="sub_income">
                    <span class="month_arrow font">└</span>
                    <span class="money_text font">카드지출</span>
                    <span class="sub_money font">${MONTH_MONEY.data.expenditure_type.expenditure_card.format()}</span>
                </li>
                <li>
                    <span class="symbol font">=</span>
                    <span class="money_text font">수입 - 지출</span>
                    <span class="money font">${MONTH_MONEY.data.total.format()}</span>
                </li>
            </ul>
        </div>
        <div class="nav_frame nav_line">
            <div class="nav_content">
                <img id="spend_target" class="arrow_target" src="image/arrow/arrow_down.png" alt="down" />
                <h3 class="month_text font">
                    <span class="font">이달의 지출 분석</span>
                </h3>
            </div>
            <div class="percent_container nav_space">
                <div class="percent">
                    <div class="saving_percent"></div>
                    <div class="spend_percent"></div>
                    <span id="saving_text" class="saving_text font">${savingPercent}%</span>
                    <span id="spend_text" class="spend_text font">${spendPercent}%</span>
                </div>
                <div class="percent_label">
                    <div class="saving_color"></div>
                    <span class="font">저축/보험</span>
                    <div class="spend_color"></div>
                    <span class="font">소비지출</span>
                </div>
                <ul>`;

function rankGraph(cost, type, i) {
    let total = Math.round(cost/ percentSpending * 100);
    navPaint +=
                `<li class="graph_space">
                        <div class="graph_ranking">
                            <div class="graph_div">
                                <div class="graph_frame">`;
    (total >= 50)
        ? navPaint += `<div class="graph graph_color_${i}" style="width:100%"></div>`
        : navPaint += `<div class="graph graph_color_${i}" style="width: ${total}%"></div>`;
    navPaint +=
                                `</div>
                                <span class="graph_percent font">${total}%</span>
                                <span class="graph_text font">${type}</span>
                            </div>
                        </div>
                    </li>`;
}

for(let i = 0; i < 4; i++){
    (MONTH_MONEY.spendingMonth.spendingRanks[i] !== undefined) ?
        rankGraph(MONTH_MONEY.spendingMonth.spendingRanks[i].cost, MONTH_MONEY.spendingMonth.spendingRanks[i].type, i)
        : navPaint +=
            `<li class="graph_space">
                        <div class="graph_ranking">
                            <div class="graph_div">
                                <div class="graph_frame"></div>
                                <span class="graph_percent font">0%</span>
                                <span class="graph_text font">미분류</span>
                            </div>
                        </div>
                    </li>`;

}
navPaint +=
                `</ul>
            </div>
        </div>
        <div class="nav_frame nav_line">
            <div class="nav_content">
                <img id="property_target" class="arrow_target" src="image/arrow/arrow_down.png" alt="down" />
                <h3 class="month_text font">
                    <span class="font">총누적자산</span>
                </h3>
            </div>
            <ul class="property_money nav_space">
                <li>
                    <span class="property_text font">자산합계</span>
                    <span class="money font">${MONTH_MONEY.cumulative.total_cost.format()}</span>
                </li>
                <li class="property_sub_text">
                    <span class="month_arrow font">└</span>
                    <span class="money_text font">현금잔액</span>
                    <span class="sub_money font">${MONTH_MONEY.cumulative.sum_money.format()}</span>
                </li>
                <li class="property_sub_text">
                    <span class="month_arrow font">└</span>
                    <span class="money_text font">예금</span>
                    <span class="sub_money font">${MONTH_MONEY.cumulative.deposit.format()}</span>
                </li>
                <li class="property_sub_text">
                    <span class="month_arrow font">└</span>
                    <span class="money_text font">적금</span>
                    <span class="sub_money font">${MONTH_MONEY.cumulative.save_money.format()}</span>
                </li>
                <li class="property_sub_text">
                    <span class="month_arrow font">└</span>
                    <span class="money_text font">펀드</span>
                    <span class="sub_money font">${MONTH_MONEY.cumulative.fund.format()}</span>
                </li>
                <li class="property_sub_text">
                    <span class="month_arrow font">└</span>
                    <span class="money_text font">보험</span>
                    <span class="sub_money font">${MONTH_MONEY.cumulative.insurance.format()}</span>
                </li>
                <li class="property_sub_text">
                    <span class="month_arrow font">└</span>
                    <span class="money_text font">투자</span>
                    <span class="sub_money font">${MONTH_MONEY.cumulative.investment.format()}</span>
                </li>
                <li class="property_sub_text">
                    <span class="month_arrow font">└</span>
                    <span class="money_text font">기타</span>
                    <span class="sub_money font">${MONTH_MONEY.cumulative.etc.format()}</span>
                </li>
            </ul>
        </div>
    </div>
</nav>`;

document.write(navPaint);