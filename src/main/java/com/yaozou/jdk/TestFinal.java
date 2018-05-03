package com.yaozou.jdk;/**
 * created by yaozou on 2018/5/3
 */

/**
 *
 * @author yaozou
 * @create 2018-05-03 21:04
 **/
public class TestFinal {
    public static void main(String[] args){
        StringBuffer sb1 = new StringBuffer();
        sb1.toString();
        StringBuilder sb2 = new StringBuilder();

        String a = "hello2";
        final String b = "hello";
        String d = "hello";
        String c = b + 2;
        String e = d + 2;
        System.out.println((a == c));
        System.out.println((a == e));
    }

}
