var detail_wraps;
var cookies;

function init_items() {
    detail_wraps = document.getElementsByClassName("panel-detail-wrap");
    cookies = document.cookie.split("; ");
}

function show(index) {
    switch(index) {
        case 0:location.href = 'blog?type=userpage';break;
        case 1:location.href = "blog?type=userpage&target=changePWD";break;
        case 2:location.href = 'blog?type=userpage&target=manage';break;
    }
}

function submit_modify_user() {
    var display_name = document.getElementById("display-name");

    if (display_name.value.length < 3 || display_name.value.length > 20) {
        alert('昵称长度至少为3！');
        display_name.focus();
        return;
    }

    var username = document.getElementById("username").value;

    for(var i = 0;i < cookies.length;i ++) {
        var cookie = cookies[i].split("=");
        if (cookie[0]=="username" && cookie[1] != username) {
            alert('请不要擅自修改用户名！');
            location.reload();
            return;
        }
    }

    document.getElementById("modify-user").submit();
}

function change_password() {
    var old_password = document.getElementById("old-password");
    var error_old_password = document.getElementById("error-old-password");

    for(var i = 0;i < cookies.length;i ++) {
        var cookie = cookies[i].split("=");
        if (cookie[0]=="password" && cookie[1] != old_password.value) {
            error_old_password.innerText = "原密码错误";
            error_old_password.style.color = "red";
            old_password.focus();
            return;
        } else {
            error_old_password.innerText = "";
        }
    }

    var new_password = document.getElementById("new-password");
    var error_new_password = document.getElementById("error-new-password");

    if (new_password.value.length < 6 || new_password.value.length > 20) {
        error_new_password.innerText = "密码长度至少为6";
        error_new_password.style.color = "red";
        new_password.focus();
        return;
    } else {
        error_old_password.innerText = "";
    }

    document.getElementById("modify-pwd").submit();
}