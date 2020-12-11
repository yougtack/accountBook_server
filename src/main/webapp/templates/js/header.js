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