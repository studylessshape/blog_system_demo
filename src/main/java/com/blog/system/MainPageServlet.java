package com.blog.system;

import dao.*;
import entity.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.*;

public class MainPageServlet extends HttpServlet {
    BlogPageInfoDao blogInfoDao = new BlogPageInfoDao();
    BlogPageContentDao blogContentDao = new BlogPageContentDao();

    UserDao userDao = new UserDao();
    UserAuthorityDao userAuthorityDao = new UserAuthorityDao();

    CommentDao commentDao = new CommentDao();

    List<UserAuthority> authorities = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        Map parameters = request.getParameterMap();
        PrintWriter out = response.getWriter();

        if (authorities == null || authorities.size() == 0) {
            authorities = userAuthorityDao.find(null);
        }

        User user = getCookieLogin(request);
        setUser(user, request, response);

        if (parameters.size() == 0) {
            basePage(request, response);
        } else if (parameters.containsKey("type")) {
            typeAction(request, response, out);
        }
    }

    User getCookieLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie username_cookie = null;
        Cookie password_cookie = null;
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username_cookie = cookie;
                } else if (cookie.getName().equals("password")) {
                    password_cookie = cookie;
                }
            }
        }

        User user = null;
        if (username_cookie != null && password_cookie != null) {
            user = userDao.login(username_cookie.getValue(), password_cookie.getValue());
        }

        return user;
    }

    void setCookieLogin(User user, HttpServletResponse response) {
        Cookie username = new Cookie("username", user.getUserName());
        Cookie password = new Cookie("password", user.getPassword());
        username.setMaxAge(2592000);
        password.setMaxAge(2592000);
        username.setPath("/");
        password.setPath("/");

        response.addCookie(username);
        response.addCookie(password);
    }

    void setUser(User user, HttpServletRequest request, HttpServletResponse response) {
        if (user == null) {
            request.setAttribute("isUser", false);
            Cookie killUsername = new Cookie("username", null);
            Cookie killPassword = new Cookie("password", null);
            killUsername.setPath("/");
            killPassword.setPath("/");
            killUsername.setMaxAge(0);
            killPassword.setMaxAge(0);

            response.addCookie(killUsername);
            response.addCookie(killPassword);
        } else {
            setCookieLogin(user, response);
            request.setAttribute("isUser", true);
            request.setAttribute("user", user);
            request.setAttribute("user_authority", authorities);
        }
    }

    void basePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BlogPageInfo> list = blogInfoDao.find(null);
        Collections.reverse(list);
        request.setAttribute("blog_list", list);
        request.getRequestDispatcher("/blog.jsp").forward(request, response);
    }

    void typeAction(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {
        switch(request.getParameter("type")) {
            case "read": readAction(request, response);break;
            case "login": loginAction(request,response,out);break;
            case "register": registerAction(request, response, out);break;
            case "signout": signOutAction(request, response, out);break;
            case "turn2addBlog": {
                setUser(getCookieLogin(request), request, response);
                request.getRequestDispatcher("./add_blog.jsp").forward(request, response);
                break;
            }
            case "addBlog": addBlogAction(request, out);break;
            case "del": deleteAction(request, out);break;
            case "modify": modifyAction(request, response, out);break;
            case "modifyBlog": modifyBlogAction(request, out);break;
            case "userpage": enterUserPageAction(request, response);break;
            case "modifyUser": modifyUserAction(request,out);break;
            case "ban": banUserAction(request, out);break;
            case "delUser": deleteUserAction(request, out);break;
            case "delComments": deleteCommentsAction(request, out);break;
            case "addComment": addCommentAction(request, out);break;
        }
    }

    void readAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String blogId = request.getParameter("blog_id");
        BlogPageContent bpc = blogContentDao.find(blogId).get(0);
        List<Comment> comments = commentDao.find(blogId);
        List<User> users = userDao.find(null);
        request.setAttribute("blog", bpc);
        request.setAttribute("comments", comments);
        request.setAttribute("users", users);
        request.getRequestDispatcher("one-blog.jsp").forward(request, response);
    }

    void loginAction(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userDao.login(username, password);
        setUser(user, request,response);
        if (user == null) {
            out.println("<script>alert('用户名或密码错误！')</script>");
        }

        String blogId = request.getParameter("blog_id");
        if (blogId == null || blogId.length() == 0) {
            out.println("<script>location.href='./'</script>");
        } else {
            out.println("<script>location.href='blog?type=read&blog_id=" + blogId + "'</script>");
        }
    }

    void registerAction(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String displayName = request.getParameter("display-name");
        if (username != null && username.length() > 0) {
            User user = userDao.findByUsername(username);
            if (user != null) {
                out.println("<script>alert('账户已存在！')</script>");
                setUser(null, request, response);
            } else {
                if (password != null && password.length() > 0 && displayName != null && displayName.length() > 0) {
                    user = new User(0, username, password, displayName, 3);
                    if (userDao.addSql(user)) {
                        user = userDao.findByUsername(username);
                        if (user != null) {
                            setUser(user, request, response);
                            out.println("<script>alert('创建成功！');location.href='/blog_system'</script>");
                            return;
                        }
                    }
                }
                setUser(null, request, response);
                out.println("<script>alert('创建失败！')</script>");
            }
        }
        out.println("<script>location.href='./register.jsp'</script>");
    }

    void signOutAction(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        setUser(null, request, response);
        String blogId = request.getParameter("blog_id");
        if (blogId == null || blogId.length() == 0) {
            out.println("<script>location.href='./'</script>");
        } else {
            out.println("<script>location.href='blog?type=read&blog_id=" + blogId + "'</script>");
        }
    }

    void addBlogAction(HttpServletRequest request, PrintWriter out) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+:08:00"));
        BlogPageContent dpc = new BlogPageContent(
                0,
                request.getParameter("blog-title"),
                request.getParameter("blog-summary"),
                new Date(cal.get(Calendar.YEAR) - 1900, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)),
                request.getParameter("content")
        );
        if (blogContentDao.addSql(dpc)) {
            out.println("<script>alert('成功添加新博客！')</script>");
            List<BlogPageInfo> listInfo = blogInfoDao.find(null);
            int newId = listInfo.get(listInfo.size() - 1).getId();
            out.println("<script>location.href='./blog?type=read&blog_id="+newId+"'</script>");
        } else {
            out.println("<script>alert('添加失败！')</script>");
            out.println("<script>history.go(-1);</script>");
        }
    }

    void deleteAction(HttpServletRequest request, PrintWriter out) {
        User user = getCookieLogin(request);
        for (UserAuthority ua : authorities) {
            if (user.getAuthority() == ua.getId() && ua.getName().equals("用户")) {
                out.println("<script>alert('权限不足！')</script>");
                out.println("<script>location.href='./'</script>");
                return;
            }
        }
        String blog_id = request.getParameter("blog_id");
        if (blog_id != null && blog_id.length() > 0) {
            if (!blogInfoDao.deleteSql(blog_id)) {
                out.println("<script>alert('删除失败！')</script>");
            }
        } else {
            out.println("<script>alert('无法通过空值查找博客！')</script>");
        }
        out.println("<script>location.href='./'</script>");
    }

    void modifyAction(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {
        String blog_id = request.getParameter("blog_id");
        if (blog_id != null && blog_id.length() > 0) {
            BlogPageContent bpc = blogContentDao.find(blog_id).get(0);
            request.setAttribute("blog_content", bpc);
            request.getRequestDispatcher("./modify_blog.jsp").forward(request, response);
        } else {
            out.println("<script>alert('无法通过空值查找博客！')</script>");
        }
        out.println("<script>location.href='./'</script>");
    }

    void modifyBlogAction(HttpServletRequest request, PrintWriter out) {
        BlogPageContent blogPageContent = new BlogPageContent(
                Integer.parseInt(request.getParameter("blog-id")),
                request.getParameter("blog-title"),
                request.getParameter("blog-summary"),
                null,
                request.getParameter("content")
        );

        if (blogContentDao.updateSql(blogPageContent)) {
            out.println("<script>location.href='./blog?type=read&blog_id="+blogPageContent.getBlog_id()+"'</script>");
        } else {
            out.println("<script>alert('更新失败！')</script>");
            out.println("<script>history.go(-1);</script>");
        }
    }

    void enterUserPageAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getCookieLogin(request);
        setUser(getCookieLogin(request), request, response);

        boolean isAdmin = false;

        for (UserAuthority ua : authorities) {
            if (ua.getId() == user.getId()) {
                if (ua.getName().equals("管理员") || ua.getName().equals("系统")) {
                    isAdmin = true;
                    List<User> users = userDao.find(null);
                    List<UserState> userStates = userDao.findUserState(null);

                    for (int i = 0;i != users.size();i ++) {
                        if (users.get(i).getUserName().equals("root")) {
                            users.remove(i);
                            break;
                        }
                    }

                    for (User u : users) {
                        for (int i = 0; i < userStates.size(); i ++) {
                            if (u.getId() == userStates.get(i).getUserId()) {
                                u.setState(userStates.get(i).getState());
                                userStates.remove(i);
                                break;
                            }
                        }
                    }
                    request.setAttribute("all_users", users);
                    break;
                }
            }
        }
        String target=request.getParameter("target");
        if (target != null && target.equals("manage") && isAdmin) {
            request.getRequestDispatcher("./user_manage.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("./user_info.jsp").forward(request, response);
        }
    }

    void modifyUserAction(HttpServletRequest request, PrintWriter out) {
        String id = request.getParameter("id");
        User user = userDao.find(id).get(0);
        String display_name = request.getParameter("display-name");
        String new_password = request.getParameter("new-password");
        user.setName(display_name);
        user.setPassword(new_password);

        if (userDao.updateSql(user)) {
            out.println("<script>alert('修改成功！重新登录以确认密码！')</script>");
            out.println("<script>location.href='./blog?type=signout'</script>");
        } else {
            out.println("<script>alert('修改失败！')</script>");
            out.println("<script>history.go(-1)</script>");
        }
    }

    void banUserAction(HttpServletRequest request, PrintWriter out) {
        User user = getCookieLogin(request);
        String id = request.getParameter("user_id");
        User target_user = userDao.find(id).get(0);
        if (target_user == null || user == null) {
            out.println("<script>alert('用户不存在！');history.go(-1)</script>");
            return;
        } else if (target_user.getId() == user.getId()){
            out.println("<script>alert('不能对自己操作！');history.go(-1)</script>");
            return;
        }

        boolean isAdmin = false;
        boolean isRoot = false;

        boolean isTargetAdmin = false;
        boolean isTargetRoot = false;

        for (UserAuthority ua : authorities) {
            if (ua.getId() == user.getAuthority()) {
                if ("管理员".equals(ua.getName()) || "系统".equals(ua.getName())) {
                    isAdmin = true;
                    if ("系统".equals(ua.getName())) {
                        isRoot = true;
                    }
                    break;
                }
            }
        }

        for (UserAuthority ua : authorities) {
            if (ua.getId() == target_user.getAuthority()) {
                if ("管理员".equals(ua.getName()) || "系统".equals(ua.getName())) {
                    isTargetAdmin = true;
                    if ("系统".equals(ua.getName())) {
                        isTargetRoot = true;
                    }
                    break;
                }
            }
        }

        if (!isAdmin
            || (!isRoot && (isTargetRoot || isTargetAdmin))
            || (isRoot && isTargetRoot)
        ) {
            out.println("<script>alert('权限不足！');history.go(-1)</script>");
            return;
        }

        int state = Integer.parseInt(request.getParameter("state"));
        switch (state) {
            case 1:
            case 0:target_user.setState(state);break;
            default:out.println("<script>alert('未知状态参数！');history.go(-1)</script>");return;
        }

        if (!userDao.updateState(target_user)) {
            out.println("<script>alert('更改状态失败！');</script>");
        }
        out.println("<script>location.href='blog?type=userpage&target=manage'</script>");
    }

    void deleteUserAction(HttpServletRequest request, PrintWriter out) {
        User user = getCookieLogin(request);
        String id = request.getParameter("user_id");
        User target_user = userDao.find(id).get(0);
        if (user == null || target_user == null) {
            out.println("<script>alert('用户不存在！');history.go(-1)</script>");
            return;
        } else if (target_user.getId() == user.getId()){
            out.println("<script>alert('不能对自己操作！');history.go(-1)</script>");
            return;
        }

        boolean isRoot = false;

        boolean isTargetRoot = false;

        for (UserAuthority ua : authorities) {
            if (ua.getId() == user.getAuthority()) {
                if ("系统".equals(ua.getName())) {
                    isRoot = true;
                    break;
                }
            }
        }

        for (UserAuthority ua : authorities) {
            if (ua.getId() == target_user.getAuthority()) {
                if ("系统".equals(ua.getName())) {
                    isTargetRoot = true;
                    break;
                }
            }
        }

        if (!isRoot || isTargetRoot) {
            out.println("<script>alert('权限不足！');history.go(-1)</script>");
            return;
        }

        if (!userDao.deleteSql(id)) {
            out.println("<script>alert('删除失败！');</script>");
        }
        out.println("<script>location.href='blog?type=userpage&target=manage';</script>");
    }

    void deleteCommentsAction(HttpServletRequest request, PrintWriter out) {
        User cUser = getCookieLogin(request);
        if (cUser == null) {
            out.println("<script>alert('你还未登录！');history.go(-1)</script>");
            return;
        }

        String userId=request.getParameter("user_id");
        User targetUser = userDao.find(userId).get(0);

        boolean isAdmin = false;
        boolean isRoot = false;

        boolean isTargetAdmin = false;
        boolean isTargetRoot = false;

        for (UserAuthority ua : authorities) {
            if (cUser.getAuthority() == ua.getId()) {
                if (ua.getName().equals("管理员")) {
                    isAdmin = true;
                } else if (ua.getName().equals("系统")) {
                    isAdmin = true;
                    isRoot = true;
                }
            } else if (targetUser.getAuthority() == ua.getId()) {
                if (ua.getName().equals("管理员")) {
                    isTargetAdmin = true;
                } else if (ua.getName().equals("系统")) {
                    isAdmin = true;
                    isTargetRoot = true;
                }
            }
        }

        if (cUser.getId() != targetUser.getId()) {
            if (!isAdmin || (isAdmin && !isRoot && (isTargetAdmin || isTargetRoot))) {
                out.println("<script>alert('权限不足！');history.go(-1)</script>");
                return;
            }
        }

        String blogId = request.getParameter("blog_id");
        String commentId = request.getParameter("comment_id");

        if (commentDao.deleteSql(commentId)) {
            out.println("<script>location.href='blog?type=read&blog_id="+blogId+"'</script>");
        } else {
            out.println("<script>alert('删除失败！');history.go(-1)</script>");
        }
    }

    void addCommentAction(HttpServletRequest request, PrintWriter out) {
        User user = getCookieLogin(request);
        if (user == null) {
            out.println("<script>alert('你还未登录！');history.go(-1)</script>");
            return;
        }

        if (user.getState() == 1) {
            out.println("<script>alert('账户已被禁言！');history.go(-1)</script>");
            return;
        }

        String blogId = request.getParameter("blog_id");
        String content = request.getParameter("comment_content");

        Comment comment = new Comment(0, Integer.parseInt(blogId), user.getId(), content);

        if (commentDao.addSql(comment)) {
            out.println("<script>location.href='blog?type=read&blog_id="+blogId+"'</script>");
        } else {
            out.println("<script>alert('发表失败！');history.go(-1)</script>");
        }
    }
}