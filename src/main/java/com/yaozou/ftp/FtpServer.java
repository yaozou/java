package com.yaozou.ftp;

import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: ftp服务器
 * @Author yao.zou
 * @Date 2019/9/10 0010
 * @Version V1.0
 **/
public class FtpServer {
    public static void main(String[] args) throws FtpException {
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory  listenerFactory       = new ListenerFactory();
        // 监听2121端口
        listenerFactory.setPort(2121);

        // 设置监听
        Listener listener = listenerFactory.createListener();
        serverFactory.addListener("default",listener);

        Map<String, Ftplet> ftpLets = new HashMap<>();
        ftpLets.put("ftpService",new FtpService());
        serverFactory.setFtplets(ftpLets);

        BaseUser user = new BaseUser();
        user.setName("admin");
        user.setPassword("123456");
        user.setHomeDirectory("E:\\\\FTPServerPath");

        List<Authority> authorities = new ArrayList<>();
        authorities.add(new WritePermission());
        user.setAuthorities(authorities);

        serverFactory.getUserManager().save(user);

        /**
         * 配置文件管理用户
         */
        /*PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        userManagerFactory.setFile(new File(""));
        serverFactory.setUserManager(userManagerFactory.createUserManager());*/

        org.apache.ftpserver.FtpServer server = serverFactory.createServer();
        server.start();
    }
}
