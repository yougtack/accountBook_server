let monthReportView = document.getElementById('month_report_view'),
    yearReportView = document.getElementById('year_report_view'),
    spendReportView = document.getElementById('spend_report_view');

(function reset() {
    monthReportView.style.display = 'block';
    yearReportView.style.display = 'none';
    spendReportView.style.display = 'none';
})();

document.getElementById('month_report').addEventListener('click', () => {
    monthReportView.style.display = 'block';
    yearReportView.style.display = 'none';
    spendReportView.style.display = 'none';
});

document.getElementById('year_report').addEventListener('click', () => {
    monthReportView.style.display = 'none';
    yearReportView.style.display = 'block';
    spendReportView.style.display = 'none';
});

document.getElementById('spend_report').addEventListener('click', () => {
    monthReportView.style.display = 'none';
    yearReportView.style.display = 'none';
    spendReportView.style.display = 'block';
});

window.addEventListener('load', () => {
    let now = new Date();
    let day = new Array('일', '월', '화', '수', '목', '금', '토');
    let month = ((now.getMonth() + 1) <= 9) ? '0' + (now.getMonth() + 1) : (now.getMonth() + 1);
    let date = ((now.getDate() + 1) <= 9) ? '0' + now.getDate() : now.getDate();
    document.getElementById('report_today').innerText =
        now.getFullYear() + "." + month + "." + date + ".(" + day[now.getDay()] + ")";
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