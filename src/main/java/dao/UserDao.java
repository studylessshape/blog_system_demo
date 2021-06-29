package dao;

import entity.User;
import entity.UserState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IDao<User> {

    public User findByUsername(String username) {
        String sql = "select a.id ,a.username ,a.pwd ,a.name ,a.authority ,b.state \n" +
                "from userinfo a,user_state b\n" +
                "where a.id=b.user_id and username=?";
        User user = null;

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBhelper.getConnection("blog_db");
            pst = conn.prepareStatement(sql);
            pst.setString(1,username);
            rs = pst.executeQuery();
            if(rs.next()) {
                user = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5)
                );
                user.setState(rs.getInt(6));
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
                User user = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5)
                );
                list.add(user);
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
        boolean isAdd =  DBhelper.updateSql("blog_db", sql, new Object[] {
                null,
                user.getUserName(),
                user.getPassword(),
                user.getName(),
                user.getAuthority()
        });

        if (isAdd) {
            List<User> users = find(null);
            String sqlState = "insert into user_state values(?,?)";
            int id = users.get(users.size()-1).getId();
            isAdd = DBhelper.updateSql("blog_db", sqlState, new Object[] { id, 0 });
        }

        return isAdd;
    }

    public List<UserState> findUserState(String id) {
        String sql = "select * from user_state ";
        if (id != null) {
            sql += " where id=" + id;
        }

        List<UserState> list = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBhelper.getConnection("blog_db");
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                UserState userState = new UserState(
                        rs.getInt(1),
                        rs.getInt(2)
                );
                list.add(userState);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBhelper.close(conn, pst, rs);
        }
        return list;
    }

    @Override
    public boolean updateSql(User user) {
        String sql = "update userinfo set name=?,pwd=? where id=?";

        return DBhelper.updateSql("blog_db", sql, new Object[] {
                user.getName(),
                user.getPassword(),
                user.getId()
        });
    }

    public boolean updateState(User user) {
        String sql = "update user_state set state=? where user_id=?";

        return DBhelper.updateSql("blog_db", sql, new Object[]{ user.getState(), user.getId() });
    }

    @Override
    public boolean deleteSql(String id) {
        String sql = "delete from userinfo where id=?";
        return DBhelper.updateSql("blog_db", sql, new Object[]{ id });
    }

    public User login(String username, String password) {
        String sql = "select a.id ,a.username ,a.pwd ,a.name ,a.authority ,b.state \n" +
                "from userinfo a,user_state b\n" +
                "where a.id=b.user_id and username=? and pwd=?";
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
                        rs.getInt(5)
                );
                user.setState(rs.getInt(6));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBhelper.close(conn, pst, rs);
        }

        return user;
    }
}
