package com.yaozou.jdk.base;

/**
 * @Description:
 * @author: yaozou
 * @Date: 2018/12/20 14:24
 */
public class TestSwitch {
    public static void main(String[] args){
        int n = 10;
        switch (n){
            default:
                System.out.println("default");
                break;
            case 1:
                System.out.println("1");
                break;
        }
    }
}
