package com.yaozou.ftp;

import org.apache.ftpserver.ftplet.*;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @Description:ftp服务
 * @Author yao.zou
 * @Date 2019/9/11 0011
 * @Version V1.0
 **/
public class FtpService extends DefaultFtplet {
    @Override
    public FtpletResult onUploadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        //获取当前路径
        String path = session.getFileSystemView().getWorkingDirectory().getAbsolutePath();
        String[] picInfoArr = session.getFileSystemView().getWorkingDirectory().getAbsolutePath().split("/");
        //获取文件名
        String filename = request.getArgument();
        InetSocketAddress serverAddress = session.getServerAddress();
        System.out.println("upload file end......");
        System.out.println("file's name is "+filename);
        return super.onUploadEnd(session, request);
    }
}
