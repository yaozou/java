package com.yaozou.algorithm.leetcode.number;

/**
 * created on 2020/9/30 15:11
 *
 * @author yaozou
 * @since v1.0.0
 */
public class Palindrome {
    /**
     * 9. 回文数
     * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     */
    public boolean isPalindrome(int x) {

        if (x < 0 || x == Integer.MAX_VALUE){
            return false;
        }
        if(x <= 9){
            return true;
        }
        // 将数字转化为字符数组
        int [] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999,
                99999999, 999999999, Integer.MAX_VALUE };
        int size;
        for (int i=0; ; i++){
            if (x <= sizeTable[i]){size=i+1;break;}
        }

        int cover = 0,next = x;
        for (int i = 0;i<size;i++){
            int m = (int)Math.pow(10,size-i-1);
            int n = next/m;
            next = next%m;
            cover += n*Math.pow(10,i);
        }

        return cover == x;
    }
    public static void main(String[] args) {
        Palindrome p = new Palindrome();
        System.out.println(p.isPalindrome(123321));
    }
}
