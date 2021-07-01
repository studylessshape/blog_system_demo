<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="zh-hans">

<head>
    <meta charset="UTF-8" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
    <meta http-equiv="description" content="This is my page" />
    <link rel="stylesheet" type="text/css" href="css/blog.css" />
    <script src="js/jquery-1.4.2.min.js"></script>
    <script src="js/login.js"></script>
    <title>我的博客</title>
</head>

<body>
    <div class="nav">
        <div class="nav-child blog-name">
            <a class="a-decoration-none a-color-inherit" href="/blog_system">我的博客</a>
        </div>
        <div class="nav-child user">
            <c:choose>
                <c:when test="${isUser}">
                    <div class="user-child">
                        Hello,
                        <div style="display: inline-block; font-weight: bold">
                            <a class="a-button-type2" href="blog?type=userpage">${user.name}</a>
                        </div>
                    </div>
                    <div class="user-child">
                        <a href="./blog?type=signout" class="a-button">退出</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="user-child">
                        <a id="login-button" class="a-button" href="javascript:show_login();">登录</a>
                    </div>
                    <div class="user-child">
                        <a id="register-button" class="a-button" href="./register.jsp">注册</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="post">
        <div class="title-wrap">
            <div class="page-title">
                <h1>我的博客</h1>
            </div>
            <div class="contact-details">
                <div class="contact-item"></div>
            </div>
        </div>
        <div class="post-wrap">
            <c:forEach items="${blog_list}" var="bl">
                <div class="one-blog-wrap">
                    <div class="one-post-wrap">
                        <h2><a class="a-decoration-none a-color-inherit" href="blog?type=read&blog_id=${bl.id}">${bl.title}</a></h2>
                        <p>${bl.summary}</p>
                        <p class="date">${bl.publishDate}</p>
                    </div>
                    <c:if test="${isUser}">
                        <c:forEach items="${user_authority}" var="ua">
                            <c:if test="${ua.id == user.authority}">
                                <c:choose>
                                    <c:when test="${ua.name eq '管理员'}">
                                        <div class="blog-button">
                                            <a class="a-button" href="blog?type=modify&blog_id=${bl.id}">编辑</a>
                                            <a class="a-button"
                                                href="javascript:if(confirm('确认删除？')){location.href='blog?type=del&blog_id=${bl.id}'}">删除</a>
                                        </div>
                                    </c:when>
                                    <c:when test="${ua.name eq '系统'}">
                                        <div class="blog-button">
                                            <a class="a-button" href="blog?type=modify&blog_id=${bl.id}">编辑</a>
                                            <a class="a-button"
                                                href="javascript:if(confirm('确认删除？')){location.href='blog?type=del&blog_id=${bl.id}'}">删除</a>
                                        </div>
                                    </c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </div>

    <div id="login-block" style="visibility: hidden;">
        <div class="login">
            <div class="close"><a href="javascript:hidden_login()" style="text-align: right;" class="a-decoration-none a-color-inherit">X</a></div>
            <div class="login-title">登录帐号</div>
            <div class="input-feild">
                <div class="input">
                    <input type="text" name="username" id="username" placeholder="帐号" onkeyup='if(event.keyCode==13){check_login();}'>
                </div>
                <div class="input">
                    <input type="password" name="password" id="password" placeholder="密码" onkeyup='if(event.keyCode==13){check_login();}'>
                </div>
                <div id="error-login"></div>
                <input type="button" value="登录" onclick="check_login();">
            </div>
        </div>
    </div>

    <c:if test="${isUser}">
        <c:forEach items="${user_authority}" var="ua">
            <c:if test="${ua.id == user.authority}">
                <c:choose>
                    <c:when test="${ua.name eq '管理员'}">
                        <div class="add-blog-button">
                            <a href="blog?type=turn2addBlog">
                                <svg t="1624886050275" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                                    p-id="1190">
                                    <path
                                        d="M512 1024C229.2224 1024 0 794.7776 0 512 0 229.2224 229.2224 0 512 0 794.7776 0 1024 229.2224 1024 512 1024 794.7776 794.7776 1024 512 1024ZM716.8 486.4 537.6 486.4 537.6 307.2 486.4 307.2 486.4 486.4 307.2 486.4 307.2 537.6 486.4 537.6 486.4 716.8 537.6 716.8 537.6 537.6 716.8 537.6 716.8 486.4Z"
                                        p-id="1191" fill="#d81e06"></path>
                                </svg>
                            </a>
                        </div>
                    </c:when>
                    <c:when test="${ua.name eq '系统'}">
                        <div class="add-blog-button">
                            <a href="blog?type=turn2addBlog">
                                <svg t="1624886050275" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                                    p-id="1190">
                                    <path
                                        d="M512 1024C229.2224 1024 0 794.7776 0 512 0 229.2224 229.2224 0 512 0 794.7776 0 1024 229.2224 1024 512 1024 794.7776 794.7776 1024 512 1024ZM716.8 486.4 537.6 486.4 537.6 307.2 486.4 307.2 486.4 486.4 307.2 486.4 307.2 537.6 486.4 537.6 486.4 716.8 537.6 716.8 537.6 537.6 716.8 537.6 716.8 486.4Z"
                                        p-id="1191" fill="#d81e06"></path>
                                </svg>
                            </a>
                        </div>
                    </c:when>
                    <c:otherwise></c:otherwise>
                </c:choose>
            </c:if>
        </c:forEach>
    </c:if>
    <footer>copyright:学少何</footer>
</body>

</html>