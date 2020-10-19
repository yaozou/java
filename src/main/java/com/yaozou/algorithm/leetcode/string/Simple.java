package com.yaozou.algorithm.leetcode.string;

/**
 * created on 2020/10/19 18:05
 *
 * @author yaozou
 * @since v1.0.0
 */
public class Simple {

    /**
     * 14. 最长公共前缀
     */
    public String longestCommonPrefix(String[] strs) {
        StringBuilder prefix = new StringBuilder();
        if (strs.length == 0){return prefix.toString();}

        String minChar = strs[0];
        for (String str:strs) {
            if (minChar.length() > str.length() ){
                minChar = str;
            }
        }

        char[] chars = minChar.toCharArray();
        for (int i = 0;i<chars.length;i++){
            char c = chars[i];
            for (String str:strs) {
                if (str.equals(minChar)){continue;}
                if (c != str.toCharArray()[i]){
                    return prefix.toString();
                }
            }
            prefix.append(c);
        }

        return prefix.toString();
    }

    public static void main(String[] args) {
        Simple s = new Simple();
        System.out.println(s.longestCommonPrefix(new String[]{}));
    }


}
