var isName = false;
var isPassword = false;
var isUsername = false;

function check_name() {
    var username = $("#username").val();
    if (username.length < 3 || username.length > 20) {
        $("#error-user").text("账户过长或过短");
        $("#error-user").css("color", "red");
        isUsername = false;
        return;
    }
    $.post("blog", { "type":"register", "target":"check", "username":username}, back, "Text");
    function back(date) {
        if (date == 'user-exist') {
            $("#error-user").text("账户已存在");
            $("#error-user").css("color", "red");
            isUsername =  false;
        } else if (date == 'user-none') {
            $("#error-user").text("√");
            $("#error-user").css("color", "green");
            isUsername = true;
        }
    }
}

function check_display_name() {
    var display_name = $("#display-name").val();
    if (display_name.length < 3 || display_name.length > 20) {
        $("#error-name").text("昵称长度至少为3！");
        $("#error-name").css("color", "red");
        isName = false;
    } else {
        $("#error-name").text("√");
        $("#error-name").css("color", "green");
        isName = true;
    }
}

function check_password() {
    var password = $("#password").val();
    if (password.length < 6 || password.length > 20) {
        $("#error-pwd").text("密码长度至少为6！");
        $("#error-pwd").css("color", "red");
        isPassword = false;
    } else {
        $("#error-pwd").text("√");
        $("#error-pwd").css("color", "green");
        isPassword = true;
    }
}

// $(function() {
//     $("#display-name").oninput(check_display_name);
//     $("#username").oninput(check_name);
//     $("#password").oninput(check_password);
// })

function check_register() {
    if (isName && isPassword && isUsername) {
        $("#register-form").submit();
    } else {
        if (!isName) {
            $("#display-name").focus();
        } else if (!isUsername) {
            $("#username").focus();
        } else if (!isPassword) {
            $("#password").focus();
        }
    }
}