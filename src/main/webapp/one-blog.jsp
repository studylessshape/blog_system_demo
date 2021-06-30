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
    <meta content="always" name="referrer">
    <link rel="stylesheet" type="text/css" href="css/blog.css">
    <link rel="stylesheet" type="text/css" href="css/comment.css">
    <script src="js/login.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>
    <link rel="stylesheet" href="highlight/styles/default.min.css">
    <script src="highlight/highlight.min.js"></script>
    <script src="js/comment.js"></script>
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
        <div style="margin: 40px 25% 0 25%;">评论</div>
        <div class="comments">
            <c:forEach items="${comments}" var="comment">
                <c:forEach items="${users}" var="us">
                    <c:if test="${us.id == comment.userId}">
                        <div class="comment-wrap">
                            <div class="comment-head">
                                <div class="head-info">
                                    ${us.name} 说:
                                </div>
                            </div>
                            <div class="comment-content">
                                <div class="content-info">
                                    <c:out value="${comment.content}"></c:out>
                                </div>
                            </div>
                            <!-- 评论操作的加载 -->
                            <c:if test="${isUser}">
                                <c:choose>
                                    <c:when test="${us.id == user.id}">
                                        <div class="op-wrap">
                                            <a href="javascript:if(confirm('是否删除？')){location.href='blog?type=delComments&user_id=${comment.userId}&comment_id=${comment.id}&blog_id=${comment.blogId}'}">删除评论</a>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${user_authority}" var="ua">
                                            <c:if test="${ua.id == user.authority}">
                                                <c:choose>
                                                    <c:when test="${ua.name eq '管理员'}">
                                                        <c:forEach items="${user_authority}" var="uac">
                                                            <c:if test="${uac.id == us.authority}">
                                                                <c:choose>
                                                                    <c:when test="${uac.name eq '管理员'}"></c:when>
                                                                    <c:when test="${uac.name eq '系统'}"></c:when>
                                                                    <c:otherwise>
                                                                        <div class="op-wrap">
                                                                            <a href="javascript:if(confirm('是否删除？')){location.href='blog?type=delComments&user_id=${comment.userId}&comment_id=${comment.id}&blog_id=${comment.blogId}'}">删除评论</a>
                                                                        </div>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:when>
                                                    <c:when test="${ua.name eq '系统'}">
                                                        <div class="op-wrap">
                                                            <a href="javascript:if(confirm('是否删除？')){location.href='blog?type=delComments&user_id=${comment.userId}&comment_id=${comment.id}&blog_id=${comment.blogId}'}">删除评论</a>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise></c:otherwise>
                                                </c:choose>
                                            </c:if>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </div>
                    </c:if>
                </c:forEach>
            </c:forEach>
        </div>
        <c:if test="${user.state == 0}">
            <div class="send-comments">
                <div class="send-comment-wrap">
                    <div class="send-head">
                        发表评论:
                    </div>
                    <div class="send-content">
                        <form action="blog?type=addComment" method="post" id="add-comment">
                            <input type="hidden" name="blog_id" value="${blog.blog_id}">
                            <textarea name="comment_content" id="comment-content" cols="30" rows="10"></textarea>
                            <input type="button" value="发表" onclick="submit_comment()">
                        </form>
                    </div>
                </div>
            </div>
            <script>init_comment_js();</script>
        </c:if>
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