package com.yaozou.ftp;

import org.apache.ftpserver.ftplet.*;

import java.io.*;

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
        String filename = request.getArgument();
        String path = homeDirectory+"\\"+filename;

        System.out.println(".....upload file end......");
        System.out.println("File's path is " + path);

        File file = new File(path);
        if (file.exists()){
            FileInputStream in = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(in); //默认有8M的缓存
            byte[] bytes = new byte[1024];
            int len;
            while ((len = bis.read(bytes)) != -1){
                System.out.println(new String(bytes,0,len));
            }
        }

        return super.onUploadEnd(session, request);
    }

    public String getHomeDirectory() {
        return homeDirectory;
    }
}
