var login_block;

function init_login() {
    login_block = document.createElement('div');
    login_block.id='login-block';
    login_block.style.visibility = 'hidden';
    login_block.innerHTML = 
    "        <div class=\"login\">\n" +
    "            <div class=\"close\"><a href=\"javascript:hidden_login()\" style=\"text-align: right;\" class=\"a-decoration-none a-color-inherit\">X</a></div>\n" +
    "            <div class=\"login-title\">登录帐号</div>\n" +
    "            <form action=\"blog?type=login\" method=\"post\" id='login-form'>\n" +
    "                <div class=\"input\">\n" +
    "                    <input type=\"text\" name=\"username\" id=\"username\" placeholder=\"帐号\" maxlength=\"20\" onkeyup='if(event.keyCode==13){check_login();}'>\n" +
    "                </div>\n" +
    "                <div class=\"input\">\n" +
    "                    <input type=\"password\" name=\"password\" id=\"password\" placeholder=\"密码\" maxlength=\"20\" onkeyup='if(event.keyCode==13){check_login();}'>\n" +
    "                </div>\n" +
    "                <input type=\"button\" value=\"登录\" onclick='check_login()'>\n" +
    "            </form>\n" +
    "        </div>\n";
    document.body.appendChild(login_block);
}

function init_for_blog() {
    login_block = document.getElementById("login-block");
}

function show_login() {
    login_block.style.visibility = 'visible';
}

function hidden_login() {
    login_block.style.visibility = 'hidden';
}

function check_login() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    if (username.length < 3) {
        alert('账号长度至少为3');
        return;
    }

    if (password.length < 6) {
        alert('密码长度至少为6');
        return;
    }

    document.getElementById("login-form").submit();
}

function check_register() {
    var display_name = document.getElementById("display-name").value;
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    if (display_name.length < 3) {
        alert('昵称长度至少为3');
        return;
    }

    if (username.length < 3) {
        alert('账号长度至少为3');
        return;
    }

    if (password.length < 6) {
        alert('密码长度至少为6');
        return;
    } 

    document.getElementById("register-form").submit();
}