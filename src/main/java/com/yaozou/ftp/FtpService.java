package com.yaozou.ftp;

import org.apache.ftpserver.ftplet.*;
import java.io.IOException;

/**
 * @Description:ftp服务
 * @Author yao.zou
 * @Date 2019/9/11 0011
 * @Version V1.0
 **/
public class FtpService extends DefaultFtplet {

    private String homeDirectory = "E:\\\\FTPServerPath";

    @Override
    public FtpletResult onUploadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {

        //获取文件名
        String filename = request.getArgument();

        //获取文件绝对地址
        String path = homeDirectory+"\\"+filename;

        System.out.println(".....upload file end......");
        System.out.println("Path is " + path);
        System.out.println("File's name is " + filename);
        return super.onUploadEnd(session, request);
    }

    public String getHomeDirectory() {
        return homeDirectory;
    }
}
