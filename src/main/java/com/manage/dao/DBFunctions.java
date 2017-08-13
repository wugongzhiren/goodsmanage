package com.manage.dao;

import java.sql.*;

/**
 * Created by Administrator on 2017/8/12.
 */
public class DBFunctions {
    public static int getNumIndex() throws SQLException {
        Connection conn = null;
        String sql;
        // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
        // 避免中文乱码要指定useUnicode和characterEncoding
        // 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
        // 下面语句之前就要先创建javademo数据库
        String url = "jdbc:mysql://localhost:3306/goodsmanage?"
                + "user=root&password=root";
        sql = "select indexid from numindex";
        try {
            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
            // or:
            // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
            // or：
            // new com.mysql.jdbc.Driver();

            System.out.println("成功加载MySQL驱动程序");
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url);
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                return result.getInt("indexid");
            }

        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            conn.close();
        }
        return 0;
    }

    public static int updateIndex() throws SQLException {
        Connection conn = null;
        String sql;
        String url = "jdbc:mysql://localhost:3306/goodsmanage?"
                + "user=root&password=root";
        sql = "update numindex set indexid=indexid+1 ";
        try {
            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
            conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement(sql);
            int result = stmt.executeUpdate();
            return result;
        }
        catch (Exception e){
           e.printStackTrace();
           return 0;

        }
        finally {
            conn.close();
        }
    }
}
