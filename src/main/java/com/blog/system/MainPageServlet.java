package com.blog.system;

import dao.BlogPageContentDao;
import dao.BlogPageInfoDao;
import dao.UserAuthorityDao;
import dao.UserDao;
import entity.BlogPageContent;
import entity.BlogPageInfo;
import entity.User;
import entity.UserAuthority;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainPageServlet extends HttpServlet {
    BlogPageInfoDao bpid = new BlogPageInfoDao();
    BlogPageContentDao bpcd = new BlogPageContentDao();

    UserDao ud = new UserDao();
    UserAuthorityDao uad = new UserAuthorityDao();

    List<UserAuthority> authorities = null;
    User currentUser = null;

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
            authorities = uad.find(null);
        }

        setUser(request);

        if (parameters.size() == 0) {
            List<BlogPageInfo> list = bpid.find(null);
            Collections.reverse(list);
            request.setAttribute("blog_list", list);
            request.getRequestDispatcher("/blog.jsp").forward(request, response);
        } else if (parameters.containsKey("type")) {
            typeAction(request, response, out);
        }
    }

    void setUser(HttpServletRequest request) {
        if (currentUser == null) {
            request.setAttribute("isUser", false);
        } else {
            request.setAttribute("isUser", true);
            request.setAttribute("user", currentUser);
            request.setAttribute("user_authority", authorities);
        }
    }

    void typeAction(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {
        String type = request.getParameter("type");
        if ("read".equals(type)) {
            String blogId = request.getParameter("blog_id");
            BlogPageContent bpc = bpcd.find(blogId).get(0);
            request.setAttribute("blog", bpc);
            request.getRequestDispatcher("one-blog.jsp").forward(request, response);
        } else if ("login".equals(type)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            currentUser = ud.login(username, password);
            if (currentUser == null) {
                out.println("<script>alert('用户名或密码错误！')</script>");
            }
            String blogId = request.getParameter("blog_id");
            if (blogId == null || blogId.length() == 0) {
                out.println("<script>location.href='./'</script>");
            } else {
                out.println("<script>location.href='blog?type=read&blog_id=" + blogId + "'</script>");
            }
        } else if ("register".equals(type)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String displayName = request.getParameter("display-name");
            if (username != null && username.length() > 0) {
                User user = ud.findByUsername(username);
                if (user != null) {
                    out.println("<script>alert('账户已存在！')</script>");
                } else {
                    if (password != null && password.length() > 0 && displayName != null && displayName.length() > 0) {
                        user = new User(0, username, password, displayName, 3);
                        if (ud.addSql(user)) {
                            currentUser = ud.findByUsername(username);
                            if (currentUser != null) {
                                out.println("<script>alert('创建成功！');location.href='/blog_system'</script>");
                            } else {
                                System.out.println("User is null");
                            }
                        } else {
                            System.out.println("Add failed!");
                        }
                    }
                    out.println("<script>alert('创建失败！')</script>");
                }
            }
            out.println("<script>location.href='./register.jsp'</script>");
        } else if ("signout".equals(type)) {
            currentUser = null;
            String blogId = request.getParameter("blog_id");
            if (blogId == null || blogId.length() == 0) {
                out.println("<script>location.href='./'</script>");
            } else {
                out.println("<script>location.href='blog?type=read&blog_id=" + blogId + "'</script>");
            }
        } else if("turn2addBlog".equals(type)) {
            setUser(request);
            request.getRequestDispatcher("./add_blog.jsp").forward(request, response);
        } else if("addBlog".equals(type)) {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+:08:00"));
            BlogPageContent dpc = new BlogPageContent(
                    0,
                    request.getParameter("blog-title"),
                    request.getParameter("blog-summary"),
                    new Date(cal.get(Calendar.YEAR) - 1900, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)),
                    request.getParameter("content")
            );
            if (bpcd.addSql(dpc)) {
                out.println("<script>alert('成功添加新博客！')</script>");
                List<BlogPageInfo> listInfo = bpid.find(null);
                int newId = listInfo.get(listInfo.size() - 1).getId();
                out.println("<script>location.href='./blog?type=read&blog_id="+newId+"'</script>");
            } else {
                out.println("<script>alert('添加失败！')</script>");
                out.println("<script>history.go(-1);</script>");
            }
        } else if("del".equals(type)) {
            for (UserAuthority ua : authorities) {
                if (currentUser.getAuthority() == ua.getId() && ua.getName().equals("用户")) {
                    out.println("<script>alert('权限不足！')</script>");
                    out.println("<script>location.href='./'</script>");
                }
            }
            String blog_id = request.getParameter("blog_id");
            if (blog_id != null && blog_id.length() > 0) {
                if (!bpid.deleteSql(blog_id)) {
                    out.println("<script>alert('删除失败！')</script>");
                }
            } else {
                out.println("<script>alert('无法通过空值查找博客！')</script>");
            }
            out.println("<script>location.href='./'</script>");
        } else if ("modify".equals(type)) {
            String blog_id = request.getParameter("blog_id");
            if (blog_id != null && blog_id.length() > 0) {
                BlogPageContent bpc = bpcd.find(blog_id).get(0);
                setUser(request);
                request.setAttribute("blog_content", bpc);
                request.getRequestDispatcher("./modify_blog.jsp").forward(request, response);
            } else {
                out.println("<script>alert('无法通过空值查找博客！')</script>");
            }
            out.println("<script>location.href='./'</script>");
        } else if ("modifyBlog".equals(type)) {
            BlogPageContent blogPageContent = new BlogPageContent(
                    Integer.parseInt(request.getParameter("blog-id")),
                    request.getParameter("blog-title"),
                    request.getParameter("blog-summary"),
                    null,
                    request.getParameter("content")
            );

            if (bpcd.updateSql(blogPageContent)) {
                out.println("<script>location.href='./blog?type=read&blog_id="+blogPageContent.getBlog_id()+"'</script>");
            } else {
                out.println("<script>alert('更新失败！')</script>");
                out.println("<script>history.go(-1);</script>");
            }
        }
    }
}