package dao;

import entity.User;
import entity.UserAuthority;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserAuthorityDao implements IDao<UserAuthority>{
    @Override
    public List<UserAuthority> find(String id) {
        String sql = "select * from user_authority ";
        if (id != null) {
            sql += "where id=" + id;
        }

        List<UserAuthority> list = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBhelper.getConnection("blog_db");
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                list.add(new UserAuthority(
                        rs.getInt(1),
                        rs.getString(2)
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
    public boolean addSql(UserAuthority userAuthority) {
        return false;
    }

    @Override
    public boolean updateSql(UserAuthority userAuthority) {
        return false;
    }

    @Override
    public boolean deleteSql(String id) {
        return false;
    }
}
