package utils;
import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class DataSourceUtil {

    private static DataSource ds;

    /**
     * 在静态代码块中创建数据源对象
     */
    static {
        // 创建druid数据源
        DruidDataSource dataSource = new DruidDataSource();

        Properties prop = new Properties();
        try {
            // 加载配置文件
            prop.load(DataSourceUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dataSource.setUsername(prop.getProperty("jdbc.username"));
        dataSource.setPassword(prop.getProperty("jdbc.password"));
        dataSource.setUrl(prop.getProperty("jdbc.url"));
        dataSource.setDriverClassName(prop.getProperty("jdbc.driverClassName"));
        dataSource.setInitialSize(Integer.parseInt(prop.getProperty("jdbc.initialSize")));
        dataSource.setMaxActive(Integer.parseInt(prop.getProperty("jdbc.maxActive")));

        ds=dataSource;
    }

    /**
     * 得到数据源
     */
    public static DataSource getDataSource() {
        return ds;
    }


    /**
     * 从连接池中得到连接对象
     */
    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * 释放资源
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        //关闭结果集
        if (rs!=null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //关闭语句对象
        if (stmt!=null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //关闭连接对象
        if (conn!=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭连接
     */
    public static void close(Connection conn, Statement stmt) {
        close(conn, stmt, null);
    }
}