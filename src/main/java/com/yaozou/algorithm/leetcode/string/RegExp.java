package com.yaozou.algorithm.leetcode.string;

/**
 * created on 2020/9/30 17:44
 *
 * @author yaozou
 * @since v1.0.0
 */
public class RegExp {
    /**
     * 10. 正则表达式匹配
     */
    public boolean isMatch(String s, String p) {
        // 一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
        if (s == null || p == null){return false;}
        // '.' 匹配任意单个字符
        // '*' 匹配零个或多个前面的那一个元素
        if ("*".equals(p) || ".*".equals(p) || "*.".equals(p)){
            return true;
        }

        return false;
    }

    public static void main(String[] args) {

    }
}
