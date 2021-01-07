const NAV_ICON = document.getElementById('nav_icon');
const NAV = document.getElementById('nav');

let nav_state = 'no';

NAV_ICON.addEventListener('click', () => {

    if(nav_state === 'no'){
        NAV.style.cssText= 'margin-left: 0px; transition: 1s; position: absolute;';
        nav_state = 'yes';
    } else {
        NAV.style.cssText= 'margin-left: -220px; transition: 1s; position: absolute;';
        nav_state = 'no';
    }
});

let profileDiv = document.getElementById('profile_div');

document.getElementsByClassName('profile')[0].addEventListener('click', () => {
    (profileDiv.style.display === 'none') ? profileDiv.style.display = 'block' : profileDiv.style.display = 'none';
});