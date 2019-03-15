package com.yaozou.algorithm.LeetCode;

/**
 * @Description:字符串
 * @author: yaozou
 * @Date: 2019/3/15 17:20
 */
public class StringCode {
    public static void main(String[] args) {
        int x = -123;
        System.out.println(reverse(x));
    }

    public static int reverse(int x) {
        if(x > Integer.MAX_VALUE || x<Integer.MIN_VALUE){
            return 0;
        }
        char[] s = (x+"").toCharArray();
        char tmp;
        int length = s.length , start = 0;
        if(x < 0){
            start = 1;
        }
        for(int i = length-1;i >= length/2;i--){
            int j = length-1-i+start;
            if(j >= i){break;}
            tmp = s[i];
            s[i] = s[j];
            s[j] = tmp;
        }
        String result = new String(s);
        long num = new Long(result);
        if(num > Integer.MAX_VALUE || num<Integer.MIN_VALUE){
            return 0;
        }
        return (int)num;
    }

    public static void reverseString(char[] s) {
        char tmp;
        int length = s.length;
        for(int i = length-1;i >= length/2;i--){
            int j = length-1-i;
            tmp = s[i];
            s[i] = s[j];
            s[j] = tmp;
        }
    }
}
