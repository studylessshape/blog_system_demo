var detail_wraps;
var cookies;

function init_items() {
    detail_wraps = document.getElementsByClassName("panel-detail-wrap");
    cookies = document.cookie.split("; ");
}

function show(index) {
    if (index == 0) {
        location.href = 'blog?type=userpage';
    } else if (index == 1) {
        location.href = 'blog?type=userpage&target=manage';
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

    var old_password = document.getElementById("old-password");
    for(var i = 0;i < cookies.length;i ++) {
        var cookie = cookies[i].split("=");
        if (cookie[0]=="password" && cookie[1] != old_password.value) {
            alert('原密码错误！');
            old_password.focus();
            return;
        }
    }

    var new_password = document.getElementById("new-password");
    if (new_password.value.length < 6 || new_password.value.length > 20) {
        alert('新密码长度至少为6！');
        display_name.focus();
        return;
    }

    document.getElementById("modify-user").submit();
}