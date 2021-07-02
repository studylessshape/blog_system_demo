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
    <meta name="referrer" content="no-referrer" />
    <link rel="stylesheet" type="text/css" href="css/blog.css" />
    <link rel="stylesheet" href="css/user.css" />
    <script src="js/user_page.js"></script>
    <title>${blog.title}</title>
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
                            ${user.name}
                        </div>
                    </div>
                    <div class="user-child">
                        <a href="./blog?type=signout&blog_id=${blog.blog_id}" class="a-button">退出</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <script>
                        location.href = "./";
                    </script>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="info-panel-wrap">
        <div class="info-panel-nav-wrap">
            <div class="nav-item" onclick="show(0);">基本信息</div>
            <div class="nav-item" onclick="show(1);">修改密码</div>
            <c:forEach items="${user_authority}" var="ua">
                <c:if test="${user.authority == ua.id}">
                    <c:if test="${ua.name eq '系统'}">
                        <div class="nav-item" onclick="show(2);">用户管理</div>
                    </c:if>
                    <c:if test="${ua.name eq '管理员'}">
                        <div class="nav-item" onclick="show(2);">用户管理</div>
                    </c:if>
                </c:if>
            </c:forEach>
        </div>
        <div class="info-panel-detail-wrap">
            <form action="blog?type=changePWD" method="post" class="panel-detail-wrap" id="modify-pwd">
                <div class="detail-wrap">
                    <div class="detail-head">原密码:</div>
                    <div class="detail-content">
                        <input type="password" name="old-password" id="old-password" maxlength="20" />
                    </div>
                    <span id="error-old-password"></span>
                </div>
                <div class="detail-wrap">
                    <div class="detail-head">新密码:</div>
                    <div class="detail-content">
                        <input type="password" name="new-password" id="new-password" maxlength="20" />
                    </div>
                    <span id="error-new-password"></span>
                </div>
                <div class="detail-wrap">
                    <div class="a-button text-algin-center margin-top-10 border-radius-none padding-none height-25 width-200"
                        onclick="change_password()">
                        确认修改
                    </div>
                </div>
                <input type="hidden" name="id" value="${user.id}">
            </form>
        </div>
    </div>
    <script>init_items();</script>
</body>

</html>