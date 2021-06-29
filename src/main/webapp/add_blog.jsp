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
    <script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>
    <script src="js/update_edit.js"></script>
    <link rel="stylesheet" href="highlight/styles/default.min.css">
    <meta name="referrer" content="no-referrer" />
    <script src="highlight/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
    <title>添加博客</title>
</head>

<body>
    <div class="nav">
        <div class="nav-child blog-name">
            <a class="a-decoration-none a-color-inherit" href="/blog_system">我的博客</a>
        </div>
        <div class="nav-child user">
            <c:choose>
                <c:when test="${isUser}">
                    <c:forEach items="${user_authority}" var="ua">
                        <c:if test="${ua.id == user.authority}">
                            <c:choose>
                                <c:when test="${ua.name eq '管理员'}">
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
                                <c:when test="${ua.name eq '系统'}">
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
                                    <script>location.href = './'</script>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:forEach>
                    
                </c:when>
                <c:otherwise>
                    <script>location.href = './'</script>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="editor-wrap">
        <div class="input-edit-wrap">
            <form action="blog?type=addBlog" method="post" id="add-blog-form">
                <div class="input">
                    <div class="input-head">标题:</div>
                    <input type="text" name="blog-title" id="blog-title" maxlength="46">
                </div>
                <div class="input">
                    <div class="input-head">简介:</div>
                    <input type="text" name="blog-summary" id="blog-summary" maxlength="250">
                </div>
                <div class="input">
                    <div class="input-head">内容( Markdown 格式):</div>
                    <textarea name="content" id="input-feild" cols="30" rows="10" oninput="update_onchange();"></textarea>
                </div>
                <div class="input">
                    <input type="button" value="确定" onclick="check_add_blog()">
                </div>
            </form>
        </div>
        <div class="output-edit-wrap" id="output-feild"></div>
    </div>
    <script>init_update_edit();</script>
</body>

</html>