package com.yaozou.algorithm.leetcode.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created on 2020/9/24 15:51
 *
 * @author yaozou
 * @since v1.0.0
 */
public class StringSolution {
    public static void main(String[] args) {
        StringSolution solution = new StringSolution();
        System.out.println(solution.lengthOfLongestSubstring("abcabcbb"));
    }

    /**
     * 3. 无重复字符的最长子串
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * <p>
     * 示例 1:
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * <p>
     * 示例 2:
     * 输入: "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || "".equals(s)) {
            return 0;
        }
        if ("".equals(s.trim())) {
            return 1;
        }

        int length = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            char c = chars[i];
            boolean flag = false;
            for (int j = i+1; j < chars.length; j++) {
                if (c == chars[j]) {
                    flag = true;
                    int v = (center(i+1,j-1,chars) - i);
                    System.out.println(c+"->"+v);
                    if (length < v) {
                        length = v;
                    }
                    break;
                }
            }
            if (!flag) {
                int f = center(i+1,s.length()-1,chars);
                if (f != chars.length - 1){
                    int v = s.length() - i;
                    System.out.println(c+"->"+v);
                    if (length < v) {
                        length = v;
                    }
                }
            }
        }
        return length;
    }

    private int center(int start, int end, char[] chars) {
        for (; start < end-1; start++) {
            for (int j = start+1; j < end; j++) {
                if (chars[start] == chars[j]) {
                    return start;
                }
            }
        }
        return end;
    }
}
