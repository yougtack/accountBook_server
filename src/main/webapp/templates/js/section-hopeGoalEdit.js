let hopeGoalEdit = {
    data: [],
    tag : []
};

let now = new Date();
let month = ((now.getMonth() + 1) <= 9) ? '0' + (now.getMonth() + 1) : (now.getMonth() + 1);
let date = ((now.getDate() + 1) <= 9) ? '0' + now.getDate() : now.getDate();

window.addEventListener('load', () => {
    let day = new Array('일', '월', '화', '수', '목', '금', '토');
    document.getElementById('hopeGoal_today').innerText =
        now.getFullYear() + "." + month + "." + date + ".(" + day[now.getDay()] + ")";
});

let hope_id = location.search.split('=');

(function hopeGoalTagData() {
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", `http://3.133.28.138:8080/accountBook/hope_goal_only_type/${USER.data.username}`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            hopeGoalEdit.tag = JSON.parse(xhttp.responseText);

            for(value of hopeGoalEdit.tag) {
                document.getElementById('hopeGoal_content_tag').innerHTML +=
                    `<span class="hopeGoal_tag font">${value.type}</span>`;
            }

            let hopeGoalTag = document.getElementsByClassName('hopeGoal_tag');

            for (let i = 0; i < hopeGoalTag.length; i++){
                hopeGoalTag[i].addEventListener('click', () => {
                    document.getElementById('click_tag').textContent = "선택태그 : " + hopeGoalTag[i].textContent;
                });
            }
        }
    };

    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send();
})();

(function hopeGoalViewData() {
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", `http://3.133.28.138:8080/accountBook/hope_goal_detail/${hope_id[1]}`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            hopeGoalEdit.data = JSON.parse(xhttp.responseText);
            document.getElementById('hopeGoal_content_title').value = hopeGoalEdit.data.title;
            document.getElementById('hopeGoal_content_cost').value = hopeGoalEdit.data.goal_cost;
            document.getElementById('hopeGoal_start_date').value = hopeGoalEdit.data.start_date;
            document.getElementById('hopeGoal_end_date').value = hopeGoalEdit.data.end_date;
            document.getElementById('click_tag').textContent =
                "선택태그 : " + hopeGoalEdit.data.references_type;
        }
    };

    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send();
})();

document.getElementById('hopeGoalEdit_submit').addEventListener('click', () => {
    let value = document.getElementsByClassName('hopeGoal_content_input');
    let data = {
        title: "",
        goal_cost: "",
        start_date: document.getElementById('hopeGoal_start_date').value,
        end_date: document.getElementById('hopeGoal_end_date').value,
        username: USER.data.username,
        references_type: document.getElementById('click_tag').textContent.split(":")[1],
        hope_id: hope_id[1]
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
    xhttp.open("PUT", `http://3.133.28.138:8080/accountBook/hope_goal`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            location.href = "hopeGoal.html";
        }
    };

    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send(JSON.stringify(data));
});

document.getElementById('hopeGoalEdit_cancel').addEventListener('click', () => {
   location.href = `hopeGoalView.html?hope_id=${hope_id[1]}`;
});