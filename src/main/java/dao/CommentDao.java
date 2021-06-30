package dao;

import entity.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDao implements IDao<Comment>{

    @Override
    public List<Comment> find(String blog_id) {
        String sql = "select * from comments ";

        if (blog_id != null && blog_id.length() > 0) {
            sql += " where blog_id=" + blog_id;
        }

        Connection conn = DBhelper.getConnection("blog_db");
        PreparedStatement pst = null;
        ResultSet rs = null;

        List<Comment> list = new ArrayList<>();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Comment(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getInt(3),
                    rs.getString(4)
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
    public boolean addSql(Comment comment) {
        String sql = "insert into comments values(?,?,?,?)";

        return DBhelper.updateSql("blog_db", sql, new Object[]{
                null,
                comment.getBlogId(),
                comment.getUserId(),
                comment.getContent()
        });
    }

    @Override
    public boolean updateSql(Comment comment) {
        return false;
    }

    @Override
    public boolean deleteSql(String id) {
        String sql="delete from comments where id=?";
        return DBhelper.updateSql("blog_db", sql, new Object[] { id });
    }
}
