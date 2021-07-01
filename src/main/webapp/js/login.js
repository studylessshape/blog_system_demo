function show_login() {
    $("#login-block").css("visibility", 'visible');
}

function hidden_login() {
    $("#error-login").text("");
    $("#login-block").css("visibility", 'hidden');
}

function check_login() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    $.post("blog", { "type":"login", "username":username,"password":password }, back_login, "Text");
    function back_login(data) {
        if (data=="login-error") {
            $("#error-login").text("用户名或密码错误");
            $("#error-login").css("color", 'red');
        } else if (data=="login-success"){
            $("#error-login").text("");
            location.reload();
        }
    }
}