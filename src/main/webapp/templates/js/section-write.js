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