<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="zh-hans">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" type="text/css" href="css/blog.css">
    <script src="js/login.js"></script>
    <title>注册账户</title>
</head>
<body>
    <div class="nav">
        <div class="nav-child blog-name">
            <a class="a-decoration-none a-color-inherit" href="/blog_system">我的博客</a>
        </div>
    </div>
    <div class="register-wrap">
        <div class="register-block">
            <div class="register-title">注册账户</div>
            <form action="blog?type=register" method="post" id="register-form">
                <div class="input">
                    <div class="input-head">昵称:</div>
                    <input type="text" name="display-name" id="display-name" maxlength="20" placeholder="昵称" onkeyup='if(event.keyCode==13){check_register();}'>
                </div>
                <div class="input">
                    <div class="input-head">账户名:</div>
                    <input type="text" name="username" id="username" maxlength="20" placeholder="账户名" onkeyup='if(event.keyCode==13){check_register();}'>
                </div>
                <div class="input">
                    <div class="input-head">密码:</div>
                    <input type="password" name="password" id="password" maxlength="20" placeholder="密码" onkeyup='if(event.keyCode==13){check_register();}'>
                </div>
                <div class="buttons">
                    <input style="width: 48%;" type="button" value="注册" onclick="check_register()"><input style="width: 48%;" type="reset" value="重置">
                </div>
            </form>
        </div>
    </div>
</body>