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

let hope_id = location.search.split('=');

(function hopeGoalViewData() {
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", `http://3.133.28.138:8080/accountBook/hope_goal_detail/${hope_id[1]}`, false);

    xhttp.onreadystatechange = () => {
        if (xhttp.status !== 200) {
            console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
        } else {
            hopeGoalView.data = JSON.parse(xhttp.responseText);
            let percent = hopeGoalView.data.sum_cost * hopeGoalView.data.goal_cost / 100;
            let image = 1;

            if(percent >= 60) {
                image = 3;
            } else if (percent >= 30) {
                image = 2;
            } else {
                image = 1;
            }

            document.getElementById('hopeGoalView_image').src = `image/hopeGoal(${image}).png`;
            document.getElementById('hopeGoalView_title').innerText = hopeGoalView.data.title;
            document.getElementById('hopeGoalView_create').innerText = hopeGoalView.data.create_date;
            document.getElementById('hopeGoalView_cost').innerText = hopeGoalView.data.goal_cost.format();
            document.getElementById('hopeGoalView_tag').innerText = hopeGoalView.data.references_type;
            (percent >= 100)
                ? document.getElementById('hopeGoalView_graph').style.width = '100%'
                : document.getElementById('hopeGoalView_graph').style.width = percent + '%';
            document.getElementById('hopeGoalView_percent').innerText = `${percent}%`;
            document.getElementById('hopeGoalView_date').innerText =
                `Start ${hopeGoalView.data.start_date} ~ End ${hopeGoalView.data.end_date}`;
        }
    };

    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send();
})();

document.getElementById('hopeGoalView_delete').addEventListener('click', () => {
    if (confirm("삭제하시겠습니까?")) {
        let xhttp = new XMLHttpRequest();
        xhttp.open("DELETE", `http://3.133.28.138:8080/accountBook/hope_goal/${hope_id[1]}/${USER.data.username}`, false);

        xhttp.onreadystatechange = () => {
            if (xhttp.status !== 200) {
                console.log("HTTP ERROR", xhttp.status, xhttp.statusText);
            } else {
                location.href = 'hopeGoal.html';
            }
        };
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.send();
    }
});

document.getElementById('hopeGoalView_edit').addEventListener('click', () => {
    location.href = `hopeGoalEdit.html?hope_id=${hope_id[1]}`;
});