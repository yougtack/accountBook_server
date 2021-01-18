let hopeGoalView = {
    data: []
};

let now = new Date();
let month = ((now.getMonth() + 1) <= 9) ? '0' + (now.getMonth() + 1) : (now.getMonth() + 1);
let date = ((now.getDate() + 1) <= 9) ? '0' + now.getDate() : now.getDate();

window.addEventListener('load', () => {
    let day = new Array('일', '월', '화', '수', '목', '금', '토');
    document.getElementById('hopeGoal_today').innerText =
        now.getFullYear() + "." + month + "." + date + ".(" + day[now.getDay()] + ")";
});

