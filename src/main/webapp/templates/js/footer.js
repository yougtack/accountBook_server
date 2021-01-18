let FOOTER_COST = {
  data: []
};

(function budgetMonth() {
    let xhttp = new XMLHttpRequest();

    xhttp.open("GET", `http://localhost:8080/accountBook/budget_month/${USER.data.username}`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            FOOTER_COST.data = JSON.parse(xhttp.responseText);
        }
    };
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send();
})();

window.addEventListener('load', () => {
    let footerGraph = document.getElementById('footer_graph');
    ((FOOTER_COST.data.spending_this_month / FOOTER_COST.data.budget_this_month * 100) > 100)
        ? footerGraph.style.width = '100%'
        : footerGraph.style.width =
            `${(FOOTER_COST.data.spending_this_month / FOOTER_COST.data.budget_this_month * 100)}%`
    let footerSpend = document.getElementById('footer_spend');
    footerSpend.textContent = FOOTER_COST.data.spending_this_month.format();
   document.getElementById('footer_budget').textContent = FOOTER_COST.data.budget_this_month.format();
    if(FOOTER_COST.data.spending_this_month > FOOTER_COST.data.budget_this_month){
        footerSpend.style.color = '#ff5658'
        footerGraph.style.backgroundColor = '#ff5658'
    } else {
        footerSpend.style.color = '#03DE2F';
        footerGraph.style.backgroundColor = '#03DE2F';
    }
});