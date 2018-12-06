package com.yaozou.jdk;

/**
 * @Description:
 * @author: yaozou
 * @Date: 2018/11/30 09:34
 */
public class TestString {
    public static void main(String[] args){
        // 线程安全
        StringBuffer stringBuffer = new StringBuffer();
        // 非线程安全
        StringBuilder stringBuilder = new StringBuilder();
        //
        String a = new String("z");
        String b = "b";
        String c = a;
        String d = b;
        String f = "a"+"b";
    }
}
