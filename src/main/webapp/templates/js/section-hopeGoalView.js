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

(function hopeGoalViewData() {
    let hope_id = location.search.split('=');
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", `http://localhost:8080/accountBook/hope_goal_detail/${hope_id[1]}`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            hopeGoalView.data = JSON.parse(xhttp.responseText);
        }
    };

    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send();
})();