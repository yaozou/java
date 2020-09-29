package com.yaozou.algorithm.leetcode.string;

/**
 * created on 2020/9/29 11:50
 *
 * @author yaozou
 * @since v1.0.0
 */
public class Atoi {
    /**
     * 8. 字符串转换整数 (atoi)
     */
    public int myAtoi(String str) {
        // 1、如果第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字字符组合起来，形成一个有符号整数
        // 2、假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成一个整数。
        // 3、该字符串在有效的整数部分之后也可能会存在多余的字符，那么这些字符可以被忽略，它们对函数不应该造成影响。

        // 本题中的空白字符只包括空格字符 ' ' 。
        //假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，请返回  INT_MAX (231 − 1) 或 INT_MIN (−231)

        // 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换，即无法进行有效转换。
        // 在任何情况下，若函数不能进行有效的转换时，请返回 0

        if (str == null || "".equals(str.trim())) {
            return 0;
        }
        str = str.trim();
        int     limit = -0x7fffffff;
        int     defaultVal = 0x7fffffff;
        boolean negative = false,start=false;
        int i = 0, j = 0;

        char first = str.charAt(0);
       if ( first == '-'){
            limit = 0x80000000;
           defaultVal = 0x80000000;
           negative = true;
           j++;
        }else if (first == '+' ){
           j++;
       } else if (!isNum(first)){
            return 0;
        }

        int radix = 10;
        int digit;
        int multmin = limit / radix;
        int result = 0;
        for(;j< str.length();j++){
            if (!isNum(str.charAt(j))){
                break;
            }
            if (!start && str.charAt(j) == '0'){
                continue;
            }
            start = true;
            if (i>=radix){
                return defaultVal;
            }

            digit = Character.digit(str.charAt(j),radix);
            if (digit < 0){
                break;
            }
            if (result < multmin) {
                return defaultVal;
            }
            result *= radix;
            if (result < limit + digit) {
                return defaultVal;
            }
            result -= digit;
        }
        return negative?result:-result;
    }

    private boolean isNum(char c){
        return c >= '0' && c <= '9';
    }

    public static void main(String[] args) {
        Atoi atoi = new Atoi();
        //2147483647
        System.out.println(atoi.myAtoi("  -000000000002147483649"));
    }
}
