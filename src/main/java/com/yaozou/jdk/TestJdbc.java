package com.yaozou.jdk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

/**
 * @Description:
 * @Auther: yaozou
 * @Date: 2018/9/27 15:49
 */
public class TestJdbc {
    private Vector<Connection> pool;

    /*公有属性*/ //String url, String username,String password , String driverClassName
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private int poolSize;

    private static TestJdbc instance = null;
    Connection conn = null;

    private TestJdbc(){
        pool = new Vector<Connection>(poolSize);
        for (int i=0;i<poolSize;i++) {
            try{
                Class.forName(driverClassName);
                conn = DriverManager.getConnection(url,username,password);
                pool.add(conn);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    private static synchronized TestJdbc createPool(){
        if (instance == null) instance = new TestJdbc();
        return instance;
    }

    public static TestJdbc getInstance(){
        if (instance == null) return createPool();
        return instance;
    }

    /*返回连接到连接池*/
    public synchronized void release(){
        pool.add(conn);
    }

    /* 返回连接池中的一个数据库连接 */
    public synchronized Connection getConnection(){
        if (pool.size() > 0){
            Connection connection = pool.get(0);
            pool.remove(connection);
            return connection;
        }
        return null;
    }
}
