package dao;

import entity.BlogPageContent;
import entity.BlogPageInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlogPageContentDao implements IDao<BlogPageContent> {
    @Override
    public List<BlogPageContent> find(String id) {
        String sql = "select a.id,a.title,a.summary,a.publish_date,b.content\n" +
                "from blog a, blog_content b\n" +
                "where a.id=b.blog_id";
        if (id != null) {
            sql += " and a.id=" + id;
        }

        List<BlogPageContent> list = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBhelper.getConnection("blog_db");
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                list.add(new BlogPageContent(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getString(5)
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBhelper.close(conn, pst, rs);
        }
        return list;
    }

    @Override
    public boolean addSql(BlogPageContent blogPageContent) {
        String sqlBlogInfo = "insert into blog values(?,?,?,?);";
        String sqlBlogContent = "insert into blog_content values(?,?);";
        boolean isAdd = DBhelper.updateSql("blog_db", sqlBlogInfo, new Object[] {
                null,
                blogPageContent.getTitle(),
                blogPageContent.getSummary(),
                blogPageContent.getDate()
        });
        if (isAdd) {
            List<BlogPageInfo> contents = new BlogPageInfoDao().find(null);
            isAdd = DBhelper.updateSql("blog_db", sqlBlogContent, new Object[] {
                    contents.get(contents.size() - 1).getId(),
                    blogPageContent.getContent()
            });
            if (!isAdd) {
                String del = "delete from blog where id=?";
                DBhelper.updateSql("blog_db", del, new Object[]{contents.get(contents.size() - 1).getId()});
            }
        }
        return isAdd;
    }

    @Override
    public boolean updateSql(BlogPageContent blogPageContent) {
        String sqlInfo = "update blog set title=?, summary=? where id=?;";
        boolean isUpdate = DBhelper.updateSql("blog_db", sqlInfo, new Object[] {
           blogPageContent.getTitle(),
           blogPageContent.getSummary(),
           blogPageContent.getBlog_id()
        });
        if (isUpdate) {
            String sqlContent = "update blog_content set content=? where blog_id=?;";
            isUpdate = DBhelper.updateSql("blog_db", sqlContent, new Object[] {
               blogPageContent.getContent(),
               blogPageContent.getBlog_id()
            });
        }
        return isUpdate;
    }

    @Override
    public boolean deleteSql(String id) {
        return false;
    }
}
