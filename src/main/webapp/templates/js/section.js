window.addEventListener('load', () => {
    let now = new Date();
    let today = document.getElementById('today');
    let day = new Array('일', '월', '화', '수', '목', '금', '토');

    today.innerText = now.getFullYear() + "." + (now.getMonth() + 1) + "." + now.getDate() + ".(" + day[now.getDay()] + ")";
});

let bottom_btn = document.getElementsByClassName('bottom_btn');

window.addEventListener('load', () => {
    bottom_btn[0].style.borderBottom = 'none';
    bottom_btn[0].style.fontWeight = '500';
});

bottom_btn[0].addEventListener('click', () => {
    bottom_btn[0].style.borderBottom = 'none';
    bottom_btn[0].style.fontWeight = '500';
    bottom_btn[1].style.borderBottom = '1px solid #908F8F';
    bottom_btn[1].style.fontWeight = '200';
    bottom_btn[2].style.borderBottom = '1px solid #908F8F';
    bottom_btn[2].style.fontWeight = '200';
});

bottom_btn[1].addEventListener('click', () => {
    bottom_btn[1].style.borderBottom = 'none';
    bottom_btn[1].style.fontWeight = '500';
    bottom_btn[0].style.borderBottom = '1px solid #908F8F';
    bottom_btn[0].style.fontWeight = '200';
    bottom_btn[2].style.borderBottom = '1px solid #908F8F';
    bottom_btn[2].style.fontWeight = '200';
});

bottom_btn[2].addEventListener('click', () => {
    bottom_btn[2].style.borderBottom = 'none';
    bottom_btn[2].style.fontWeight = '500';
    bottom_btn[0].style.borderBottom = '1px solid #908F8F';
    bottom_btn[0].style.fontWeight = '200';
    bottom_btn[1].style.borderBottom = '1px solid #908F8F';
    bottom_btn[1].style.fontWeight = '200';
});