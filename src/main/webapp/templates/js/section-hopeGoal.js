let hopeGoalData = {
  data: []
};

let now = new Date();
let month = ((now.getMonth() + 1) <= 9) ? '0' + (now.getMonth() + 1) : (now.getMonth() + 1);
let date = ((now.getDate() + 1) <= 9) ? '0' + now.getDate() : now.getDate();

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
    let day = new Array('일', '월', '화', '수', '목', '금', '토');
    document.getElementById('hopeGoal_today').innerText =
        now.getFullYear() + "." + month + "." + date + ".(" + day[now.getDay()] + ")";

    hopeGoalWrite.style.display = 'none';
});

document.getElementById('hopeGoal_write_btn').addEventListener('click', () => {
    hopeGoalList.style.display = 'none';
    hopeGoalWrite.style.display = 'block';
    document.getElementById('hopeGoal_start_date').value =
        now.getFullYear() + "-" + month + "-" + date;
    document.getElementById('hopeGoal_end_date').value =
        now.getFullYear() + 1 + "-" + month + "-" + date;
});

document.getElementById('hopeGoal_submit').addEventListener('click', () => {
    let value = document.getElementsByClassName('hopeGoal_content_input');
    let data = {
        title: "",
        goal_cost: "",
        start_date: document.getElementById('hopeGoal_start_date').value,
        end_date: document.getElementById('hopeGoal_end_date').value,
        username: USER.data[0],
        references_type: ""
    };

    if(value[0].value === ""){
        alert("목적을 입력해주세요");
        return false;
    } else {
        data.title = value[0].value;
    }

    if(value[1].value === ""){
        alert("목표를 입력해주세요");
        return false;
    } else {
        data.goal_cost = value[1].value;
    }

    let xhttp = new XMLHttpRequest();
    xhttp.open("POST", `http://localhost:8080/accountBook/hope_goal`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            // location.href = "hopeGoal.html";
        }
    };

    console.log(data);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send(JSON.stringify(data));
});