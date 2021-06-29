package dao;

import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IDao<User> {

    public User findByUsername(String username) {
        String sql = "select * from userinfo where username='" + username + "'";
        User user = null;

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBhelper.getConnection("blog_db");
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                user = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5)
                );
            }
        } catch (SQLException throwables) {
            if (throwables.getErrorCode() == 1054) {
                return user;
            }
        } finally {
            DBhelper.close(conn, pst, rs);
        }
        return user;
    }

    @Override
    public List<User> find(String id) {
        String sql = "select * from userinfo ";
        if (id != null) {
            sql += "where id=" + id;
        }

        List<User> list = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBhelper.getConnection("blog_db");
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                list.add(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5)
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
    public boolean addSql(User user) {
        String sql = "insert into userinfo values(?,?,?,?,?)";

        return DBhelper.updateSql("blog_db", sql, new Object[] {
                null,
                user.getUserName(),
                user.getPassword(),
                user.getName(),
                user.getAuthority()
        });
    }

    @Override
    public boolean updateSql(User user) {
        return false;
    }

    @Override
    public boolean deleteSql(String id) {
        return false;
    }

    public User login(String username, String password) {
        String sql = "select * from userinfo where username=? and pwd=?";
        User user = null;

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBhelper.getConnection("blog_db");
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(1)
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBhelper.close(conn, pst, rs);
        }

        return user;
    }
}
