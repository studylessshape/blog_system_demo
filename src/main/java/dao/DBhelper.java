package dao;

import java.sql.*;

public class DBhelper {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("未能加载驱动！");
            e.printStackTrace();
        }
    }
    public static Connection getConnection(String dbName) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+dbName, "root", "a664554724");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    public static boolean updateSql(String dbName, String sql, Object[] params) {
        Connection conn = null;
        PreparedStatement pst = null;
        boolean isUpdate = false;
        try {
            conn = getConnection(dbName);
            pst = conn.prepareStatement(sql);
            for (int i = 0;i < params.length;i ++) {
                pst.setObject(i+1, params[i]);
            }
            isUpdate = pst.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(conn, pst, null);
        }
        return isUpdate;
    }

    public static void close(Connection conn, PreparedStatement pst, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
            if (pst != null)
                pst.close();
            if (conn != null)
                conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
