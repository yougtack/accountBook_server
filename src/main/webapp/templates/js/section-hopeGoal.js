let hopeGoalData = {
    data: [],
    tag: []
};

let now = new Date();
let month = ((now.getMonth() + 1) <= 9) ? '0' + (now.getMonth() + 1) : (now.getMonth() + 1);
let date = ((now.getDate() + 1) <= 9) ? '0' + now.getDate() : now.getDate();

(function hopeGoal() {
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", `http://localhost:8080/accountBook/hope_goal/${USER.data.username}`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            hopeGoalData.data = JSON.parse(xhttp.responseText);
        }
    };

    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send();
})();

(function hopeGoalTag() {
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", `http://localhost:8080/accountBook/hope_goal_only_type/${USER.data.username}`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            hopeGoalData.tag = JSON.parse(xhttp.responseText);
            console.log(hopeGoalData.tag);
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
    document.getElementById('hopeGoal_cnt').textContent = `내 희망목표(${hopeGoalData.data.length}/5)`;

    for (value of hopeGoalData.data){
        let hopeGoalTable = document.getElementById('hopeGoal_table');
        let hopeGoalPercent = value.sum_cost * value.goal_cost / 100;
        let image = 1;

        if(hopeGoalPercent >= 60) {
            image = 3;
        } else if (hopeGoalPercent >= 30) {
            image = 2;
        } else {
            image = 1;
        }

        hopeGoalTable.innerHTML +=
            `<div class="hopeGoal_list_div"><div class="hopeGoal_content_list" style="width: 35%; padding-left: 3px;">
                <img class="hopeGoal_image" src="image/hopeGoal(${image}).png" alt="image" />
                <div class="hopeGoal_click_div">
                    <span class="hopeGoal_title font">${value.title}</span>
                    <span class="hopeGoal_date font">
                        ${value.start_date} ~ ${value.end_date}
                    </span>
                </div>
            </div>
            <div class="hopeGoal_content_list" style="width: 20%; text-align: center;">
                <div class="hopeGoal_list_div">
                    <span class="hopeGoal_list_text font">
                        ${value.create_date}
                    </span>
                </div>
            </div>
            <div class="hopeGoal_content_list" style="width: 20%; text-align: right;">
                <div class="hopeGoal_list_div">
                    <span class="hopeGoal_list_text font" style="margin-right: 10px;">
                        ${value.goal_cost.format()}
                    </span>
                </div>
            </div>
            <div class="hopeGoal_content_list" style="width: 25%;">
                <div class="hopeGoal_list_div">
                    <div class="hopeGoal_graph_frame hopeGoal_list_text">
                        <div class="hopeGoal_graph" 
                            style="width: ${(hopeGoalPercent >= 50) ? 100 : hopeGoalPercent}%">
                        </div>
                    </div>              
                    <span class="hopeGoal_list_text hopeGoal_percent font">
                        ${hopeGoalPercent}%
                    </span>
                </div>
            </div>
        </div>`;
    }
});

document.getElementById('hopeGoal_write_btn').addEventListener('click', () => {
    if (hopeGoalData.data.length === 5){
        alert("작성할 수 있는 글의 개수는 5개입니다.");
        return false;
    }
    hopeGoalList.style.display = 'none';
    hopeGoalWrite.style.display = 'block';
    document.getElementById('hopeGoal_start_date').value =
        now.getFullYear() + "-" + month + "-" + date;
    document.getElementById('hopeGoal_end_date').value =
        now.getFullYear() + 1 + "-" + month + "-" + date;

    for(value of hopeGoalData.tag) {
        document.getElementById('hopeGoal_content_tag').innerHTML += `
            <span class="hopeGoal_tag font">${value.type}</span>`;
    }
});

document.getElementById('hopeGoal_submit').addEventListener('click', () => {
    let value = document.getElementsByClassName('hopeGoal_content_input');
    let data = {
        title: "",
        goal_cost: "",
        start_date: document.getElementById('hopeGoal_start_date').value,
        end_date: document.getElementById('hopeGoal_end_date').value,
        username: USER.data.username,
        references_type: document.getElementById('click_tag').textContent.split(":")[1]
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
            location.href = "hopeGoal.html";
        }
    };

    console.log(data);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send(JSON.stringify(data));
});

document.getElementById('hopeGoal_cancel').addEventListener('click', () => {
   location.href = "hopeGoal.html";
});

let hopeGoalTag = document.getElementsByClassName('hopeGoal_tag');
console.log(hopeGoalTag[0].textContent)

for (let i = 0; i < hopeGoalTag.length; i++){
    hopeGoalTag[i].addEventListener('click', () => {
        console.log("Hi");
       document.getElementById('click_tag').textContent = "선택태그 : " + hopeGoalTag[i].textContent;
    });
}