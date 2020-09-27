package com.yaozou.algorithm.leetcode.string;

import java.util.HashMap;
import java.util.Map;

/**
 * created on 2020/9/27 15:27
 *
 * @author yaozou
 * @since v1.0.0
 */
public class LongestPalindrome {

    /**
     * 5. 最长回文子串
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     */
    public String longestPalindrome(String s) {
        //把相同的词汇或句子,在下文中调换位置或颠倒过来,产生首尾回环的情趣,叫做回文,也叫回环
        // abbba  abbba
        // abccba
        if(s == null){return null;}
        if ("".equals(s.trim()) || s.length() == 1) {
            return s;
        }

        char[] chars = s.toCharArray();
        String value = new String(new char[]{chars[0]});
        for (int i = 0;i<chars.length-1;i++){
            int last = i;
            boolean flag = false;
            StringBuilder sb = new StringBuilder();
            sb.append(chars[i]);
            for (int j = i+1;j<chars.length;j++){
                if (chars[j] == chars[i]){
                    flag = true;
                    sb.append(chars[j]);
                    if (last != j-1){
                        break;
                    }
                    last = j;
                }else if (flag){
                    break;
                }else{
                    sb.append(chars[j]);
                }
            }
            if (flag && sb.length() > value.length() && isPalindrome(sb.toString())){
                value = sb.toString();
            }
            if (chars.length-i<=value.length()){
                break;
            }
        }
        return value;
    }

    private boolean isPalindrome(String str){
        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();
        for (int i=chars.length-1;i>=0;i--){
            sb.append(chars[i]);
        }

        return sb.toString().equals(str);
    }

    public static void main(String[] args) {
        LongestPalindrome l = new LongestPalindrome();
        System.out.println(l.longestPalindrome("bbbbbbbba"));
    }
}
