package com.yaozou.algorithm.leetcode.string;

import java.util.HashMap;
import java.util.Map;

/**
 * created on 2020/9/24 15:51
 *
 * @author yaozou
 * @since v1.0.0
 */
public class StringSolution {
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
        if ("".equals(s.trim()) || s.length() == 1) {
            return 1;
        }

        int length = 0;
        char[] chars = s.toCharArray();
        int size = chars.length;
        for (int i = 0; i <  size- 1; i++) {
            char c = chars[i];
            boolean flag = false;
            for (int j = i+1; j < size; j++) {
                if (c == chars[j]) {
                    flag = true;
                    int v = (center(i+1,j-1,chars) - i)+1;
                    System.out.println(c+"->"+v);
                    if (length < v) {
                        length = v;
                    }
                    break;
                }
            } // end of for
            if (!flag) {
                int f = center(i+1,s.length()-1,chars);
                int v = f - i+1;
                System.out.println(c+"->"+v);
                if (length < v) {
                    length = v;
                }
            }
        } // end of for
        return length;
    }

    private static int center(int start, int end, char[] chars) {
        //1 紧挨两个字符串重复
       // 2 两重复字符串之间，有多个重复字符且重复多次，计算最后一个重复得
        Map<Character,Boolean> map = new HashMap<>(16);
        int val = end;
        for (; start <= end; start++) {
            if (map.containsKey(chars[start])){
                break;
            }
            boolean flag = false;
            for (int j = start+1; j < end; j++) {
                if (chars[start] == chars[j]) {
                    val = start;
                    flag = true;
                    map.put(chars[start],true);
                    break;
                }
            }
            if (!flag){
                val = start;
            }
        }
        return val;
    }

    public static void main(String[] args) {
        /*char[] chars = "abcabcbb".toCharArray();
        System.out.println(center(1,3,chars));*/

        StringSolution solution = new StringSolution();
        System.out.println(solution.lengthOfLongestSubstring("pwwkew"));
    }
}
