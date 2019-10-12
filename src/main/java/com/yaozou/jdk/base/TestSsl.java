package com.yaozou.jdk.base;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * @author yaozou
 * @date 2019/10/12 0012 10:12
 * @since 1.0.0
 */
public class TestSsl {
    private static final String CLIENT_KEY_STORE_PASSWORD = "passw0rdsrv";
    private static final String CLIENT_TRUST_KEY_STORE_PASSWORD = "passw0rdsrv";

    public void init() {
        try {
            SSLContext ctx = SSLContext.getInstance("SSL");
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStore tks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("C:\\Users\\Administrator\\Desktop\\mqtt\\signedclientkeystore.jks"), CLIENT_KEY_STORE_PASSWORD.toCharArray());
            tks.load(new FileInputStream("C:\\Users\\Administrator\\Desktop\\mqtt\\signedclientkeystore.jks"), CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
            kmf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());
            tmf.init(tks);
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            SSLSocketFactory sslSocketFactory = ctx.getSocketFactory();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
