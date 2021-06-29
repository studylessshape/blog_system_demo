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
            <c:forEach items="${user_authority}" var="ua">
                <c:if test="${user.authority == ua.id}">
                    <c:if test="${ua.name eq '系统'}">
                        <div class="nav-item" onclick="show(1);">用户管理</div>
                    </c:if>
                    <c:if test="${ua.name eq '管理员'}">
                        <div class="nav-item" onclick="show(1);">用户管理</div>
                    </c:if>
                </c:if>
            </c:forEach>
        </div>
        <div class="info-panel-detail-wrap">
            <form action="blog?type=modifyUser" method="post" class="panel-detail-wrap" id="modify-user">
                <div class="detail-wrap flex-row">
                    <div class="detail-head">账户状态:</div>
                    <div class="detail-content">
                        <c:choose>
                            <c:when test="${user.state == 0}">
                                <div class="user-state green">正常</div>
                            </c:when>
                            <c:when test="${user.state == 1}">
                                <div class="user-state red">封禁中</div>
                            </c:when>
                            <c:otherwise>
                                <div class="user-state">未知</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="detail-wrap">
                    <div class="detail-head">昵称:</div>
                    <div class="detail-content">
                        <input type="text" name="display-name" id="display-name" value="${user.name}" maxlength="20" />
                    </div>
                </div>
                <div class="detail-wrap">
                    <div class="detail-head">用户名:</div>
                    <div class="detail-content">
                        <input type="text" name="username" id="username" disabled="disabled" value="${user.userName}"
                            maxlength="20" />
                    </div>
                </div>
                <div class="detail-wrap">
                    <div class="detail-head">原密码:</div>
                    <div class="detail-content">
                        <input type="password" name="old-password" id="old-password" maxlength="20" />
                    </div>
                </div>
                <div class="detail-wrap">
                    <div class="detail-head">新密码:</div>
                    <div class="detail-content">
                        <input type="password" name="new-password" id="new-password" maxlength="20" />
                    </div>
                </div>
                <div class="detail-wrap">
                    <div class="a-button text-algin-center margin-top-10 border-radius-none padding-none height-25 width-200"
                        onclick="submit_modify_user()">
                        确认修改
                    </div>
                </div>
                <input type="hidden" name="id" value="${user.id}">
            </form>
            <c:forEach items="${user_authority}" var="ua">
                <c:if test="${user.authority == ua.id}">
                    <c:if test="${ua.name eq '系统'}">
                        <div class="panel-detail-wrap" style="visibility: hidden;">
                            <table>
                                <thead>
                                    <tr>
                                        <th>用户ID</th>
                                        <th>昵称</th>
                                        <th>用户名</th>
                                        <th>权限</th>
                                        <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <c:forEach items="${all_users}" var="one_user">
                                    <tr>
                                        <td>${one_user.id}</td>
                                        <td>${one_user.name}</td>
                                        <td>${one_user.userName}</td>
                                        <td>
                                            <c:forEach items="${user_authority}" var="ua_chi">
                                                <c:if test="${ua_chi.id == one_user.authority}">
                                                    <c:if test="${ua_chi.name eq '系统'}">
                                                        <div class="system-authority">系统</div>
                                                    </c:if>
                                                    <c:if test="${ua_chi.name eq '管理员'}">
                                                        <div class="admin-authority">管理员</div>
                                                    </c:if>
                                                    <c:if test="${ua_chi.name eq '用户'}">
                                                        <div class="user-authority">用户</div>
                                                    </c:if>
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${one_user.state == 0}">
                                                    <div class="user-state green">正常</div>
                                                </c:when>
                                                <c:when test="${one_user.state == 1}">
                                                    <div class="user-state red">封禁中</div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="user-state">未知</div>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${one_user.state == 0}">
                                                    <a class="small-button" href="blog?type=ban&state=1&user_id=${one_user.id}">禁言</a>
                                                </c:when>
                                                <c:when test="${one_user.state == 1}">
                                                    <a class="small-button" href="blog?type=ban&state=0&user_id=${one_user.id}">解禁</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="user-state">未知</div>
                                                </c:otherwise>
                                            </c:choose>
                                            <a class="small-button" href="javascript:if(confirm('是否删除？')){location.href='blog?type=delUser&user_id=${one_user.id}'}">删除</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </c:if>
                    <c:if test="${ua.name eq '管理员'}">
                        <div class="panel-detail-wrap" style="visibility: hidden;">
                            <table>
                                <thead>
                                    <tr>
                                        <th>用户ID</th>
                                        <th>昵称</th>
                                        <th>用户名</th>
                                        <th>权限</th>
                                        <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <c:forEach items="${all_users}" var="one_user">
                                    <c:if test="${one_user.id != user.id}">
                                        <tr>
                                            <td>${one_user.id}</td>
                                            <td>${one_user.name}</td>
                                            <td>${one_user.userName}</td>
                                            <td>
                                                <c:forEach items="${user_authority}" var="ua_chi">
                                                    <c:if test="${ua_chi.id == one_user.authority}">
                                                        <c:if test="${ua_chi.name eq '系统'}">
                                                            <div class="system-authority">系统</div>
                                                        </c:if>
                                                        <c:if test="${ua_chi.name eq '管理员'}">
                                                            <div class="admin-authority">管理员</div>
                                                        </c:if>
                                                        <c:if test="${ua_chi.name eq '用户'}">
                                                            <div class="user-authority">用户</div>
                                                        </c:if>
                                                    </c:if>
                                                </c:forEach>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${one_user.state == 0}">
                                                        <div class="user-state green">正常</div>
                                                    </c:when>
                                                    <c:when test="${one_user.state == 1}">
                                                        <div class="user-state red">封禁中</div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="user-state">未知</div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${one_user.state == 0}">
                                                        <a class="small-button" href="blog?type=ban&state=1&user_id=${one_user.id}">禁言</a>
                                                    </c:when>
                                                    <c:when test="${one_user.state == 1}">
                                                        <a class="small-button" href="blog?type=ban&state=0&user_id=${one_user.id}">解禁</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="user-state">未知</div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </table>
                        </div>
                    </c:if>
                </c:if>
            </c:forEach>
        </div>
    </div>
    <script>init_items();</script>
</body>

</html>