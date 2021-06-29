package dao;

import entity.BlogPageInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlogPageInfoDao implements IDao<BlogPageInfo> {

    @Override
    public List<BlogPageInfo> find(String id) {
        String sql = "select * from blog ";
        if (id != null) {
            sql += "where id=" + id;
        }

        List<BlogPageInfo> list = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBhelper.getConnection("blog_db");
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                list.add(new BlogPageInfo(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4)
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
    public boolean addSql(BlogPageInfo blogPageInfo) {
        String sqlBlogInfo = "insert into blog values(?,?,?,?);";
        return DBhelper.updateSql("blog_db", sqlBlogInfo, new Object[] {
                null,
                blogPageInfo.getTitle(),
                blogPageInfo.getSummary(),
                blogPageInfo.getPublishDate()
        });
    }

    @Override
    public boolean updateSql(BlogPageInfo blogPageInfo) {
        return false;
    }

    @Override
    public boolean deleteSql(String id) {
        String sql = "delete from blog where id=?";

        return DBhelper.updateSql("blog_db", sql, new Object[] { id });
    }
}
