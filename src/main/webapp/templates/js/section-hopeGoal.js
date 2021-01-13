let hopeGoalData = {
  data: []
};

(function hopeGoal() {
    let xhttp = new XMLHttpRequest();

    xhttp.open("GET", `http://localhost:8080/accountBook/hope_goal/${USER.data[0]}`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            hopeGoalData.data = JSON.parse(xhttp.responseText);
            console.log(hopeGoalData.data);
        }
    };

    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send();
})();

let hopeGoalList = document.getElementById('hopeGoal_list'),
    hopeGoalWrite = document.getElementById('hopeGoal_write');

window.addEventListener('load', () => {
    let now = new Date();
    let day = new Array('일', '월', '화', '수', '목', '금', '토');
    let month = ((now.getMonth() + 1) <= 9) ? '0' + (now.getMonth() + 1) : (now.getMonth() + 1);
    let date = ((now.getDate() + 1) <= 9) ? '0' + now.getDate() : now.getDate();
    document.getElementById('hopeGoal_today').innerText =
        now.getFullYear() + "." + month + "." + date + ".(" + day[now.getDay()] + ")";

    hopeGoalWrite.style.display = 'none';
});

document.getElementById('hopeGoal_write_btn').addEventListener('click', () => {
    hopeGoalList.style.display = 'none';
    hopeGoalWrite.style.display = 'block';
});