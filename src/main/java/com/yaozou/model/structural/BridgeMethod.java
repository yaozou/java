package com.yaozou.model.structural;

import lombok.Data;

/**
 * 桥接模式
 */
public class BridgeMethod {
    public static void main(String[] args) {
        // 基本思路：桥接模式就是把事物和其具体实现分开，使他们可以各自独立的变化。
        // 桥接的用意是：将抽象化与实现化解耦，使得二者可以独立变化，
        /**
         *  例如：常用的JDBC桥DriverManager一样，JDBC进行连接数据库的时候，在各个数据库之间进行切换，基本不需要动太多的代码，甚至丝毫不用动，
         *       原因就是JDBC提供统一接口，每个数据库提供各自的实现，用一个叫做数据库驱动的程序来桥接就行了
         */
        BridgeMethod method = new BridgeMethod();
        method.run();
    }

    interface Driver{
        void connection();
    }

    class MysqlDriver implements Driver{
        public void connection() {
            System.out.println("mysql is connecting......");
        }
    }

    class OracleDriver implements Driver{
        public void connection() {
            System.out.println("oracle is connecting.......");
        }
    }

    @Data
    abstract class DriverManager{
        private Driver driver;
        public void connection(){
            driver.connection();
        }
    }

    class Client extends DriverManager{
        public void connection(){
            getDriver().connection();
        }
    }

    public void run(){
        Client client = new Client();

        /*调用第一个对象*/
        Driver mysqlDriver = new MysqlDriver();
        client.setDriver(mysqlDriver);
        client.connection();

        /*调用第二个对象*/
        Driver oracleDriver = new OracleDriver();
        client.setDriver(oracleDriver);
        client.connection();
    }
}
