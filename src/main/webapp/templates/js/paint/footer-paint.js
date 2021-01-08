(function budgetMonth() {
    let xhttp = new XMLHttpRequest();

    xhttp.open("GET", `http://localhost:8080/accountBook/budget_month/${USER.data[0]}`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            console.log(JSON.parse(xhttp.responseText));
        }
    };
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send();
})();

let footerPaint =
`<footer class="footer_wrapper">
    <div class="footer_container">
        <span class="footer_text font">이달의예산</span>
    </div>
</footer>`;

document.write(footerPaint);