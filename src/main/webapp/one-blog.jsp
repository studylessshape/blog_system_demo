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
    <meta name="referrer" content="no-referrer" />
    <link rel="stylesheet" type="text/css" href="css/blog.css">
    <script src="js/login.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>
    <link rel="stylesheet" href="highlight/styles/default.min.css">
    <script src="highlight/highlight.min.js"></script>
    <title>${blog.title}</title>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
    <div class="nav">
        <div class="nav-child blog-name">
            <a class="a-decoration-none a-color-inherit" href="/blog_system">我的博客</a>
        </div>
        <div class="nav-child user">
            <c:choose>
                <c:when test="${isUser}">
                    <div class="user-child">Hello, <div style="display: inline-block;font-weight: bold;"><a class="a-button-type2" href="blog?type=userpage">${user.name}</a></div></div>
                    <div class="user-child"><a href="./blog?type=signout&blog_id=${blog.blog_id}" class="a-button">退出</a></div>
                </c:when>
                <c:otherwise>
                    <div class="user-child"><a id="login-button" class="a-button" href="javascript:show_login();">登录</a></div>
                    <div class="user-child"><a id="register-button" class="a-button" href="./register.jsp">注册</a></div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="post post-blog">
        <div class="blog">
            <div class="blog-title-wrap"><h1><c:out value="${blog.title}"></c:out></h1></div>
            <div class="date-wrap"><p class="date">${blog.date}</p></div>
            <div class="blog-content-wrap" id="blog-content"></div>
        </div>
    </div>
    <div id="login-block" style="visibility: hidden;">
        <div class="login">
            <div class="close"><a href="javascript:hidden_login()" style="text-align: right;" class="a-decoration-none a-color-inherit">X</a></div>
            <div class="login-title">登录帐号</div>
            <form action="blog?type=login&blog_id=${blog.blog_id}" method="post">
                <div class="input">
                    <input type="text" name="username" id="username" placeholder="帐号" onkeyup='if(event.keyCode==13){check_login();}'>
                </div>
                <div class="input">
                    <input type="password" name="password" id="password" placeholder="密码" onkeyup='if(event.keyCode==13){check_login();}'>
                </div>
                <input type="submit" value="登录">
            </form>
        </div>
    </div>
    <input type="hidden" style="position: fixed;display: block; top: 0;" id="blog-content-value" value='${blog.content}'></input>

    <c:if test="${isUser}">
        <c:forEach items="${user_authority}" var="ua">
            <c:if test="${ua.id == user.authority}">
                <c:choose>
                    <c:when test="${ua.name eq '管理员'}">
                        <div class="modify-button-wrap">
                            <a class="a-button" href="blog?type=modify&blog_id=${blog.blog_id}">编辑</a>
                            <a class="a-button"
                                href="javascript:if(confirm('确认删除？')){location.href='blog?type=del&blog_id=${blog.blog_id}'}">删除</a>
                        </div>
                    </c:when>
                    <c:when test="${ua.name eq '系统'}">
                        <div class="modify-button-wrap">
                            <a class="a-button" href="blog?type=modify&blog_id=${blog.blog_id}">编辑</a>
                            <a class="a-button"
                                href="javascript:if(confirm('确认删除？')){location.href='blog?type=del&blog_id=${blog.blog_id}'}">删除</a>
                        </div>
                    </c:when>
                    <c:otherwise></c:otherwise>
                </c:choose>
            </c:if>
        </c:forEach>
    </c:if>
    <script>init_for_blog();</script>
    <script>
        var content = document.getElementById('blog-content');
        var value = document.getElementById('blog-content-value').value;

        content.innerHTML = marked(value);
        hljs.highlightAll();
    </script>
</body>
</html>