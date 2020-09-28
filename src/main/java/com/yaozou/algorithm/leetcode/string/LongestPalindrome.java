package com.yaozou.algorithm.leetcode.string;


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
        // 动态规划
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        String ans = "";

        /**
         * 对于一个子串而言，如果它是回文串，并且长度大于 222，那么将它首尾的两个字母去除之后，它仍然是个回文串。例如对于字符串 “ababa”，如果我们已经知道 “bab”是回文串，
         * 那么 “ababa” 一定是回文串，这是因为它的首尾两个字母都是 “a”。
         *
         * 动态规划的状态转移方程：
         * P(i,j)=P(i+1,j−1)∧(Si==Sj)
         * 也就是说，只有 s[i+1:j−1]是回文串，并且s的第 i和 j个字母相同时，s[i:j]才会是回文串。
         * 时间复杂度：O(n^2)，其中 n是字符串的长度。动态规划的状态总数为 O(n^2)，对于每个状态，我们需要转移的时间为 O(1)。
         * 空间复杂度：O(n^2)，即存储动态规划状态需要的空间。
         *
         */
        // l为长度
        for (int l = 0; l < n; ++l) {
            for (int i = 0; i + l < n; ++i) {
                int j = i + l;
                if (l == 0) {
                    dp[i][j] = true;
                } else if (l == 1) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[i][j] = (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]);
                }
                if (dp[i][j] && l + 1 > ans.length()) {
                    ans = s.substring(i, i + l + 1);
                }
            }
        }

        return ans;
    }

    public String longestPalindrome2(String s) {
        if (s == null || s.length() < 1){
            return "";
        }
        /** 中心扩展算法
         * 我们枚举每一种边界情况，并从对应的子串开始不断地向两边扩展。
         * 如果两边的字母相同，我们就可以继续扩展，例如从 P(i+1,j−1)扩展到 P(i,j)；
         * 如果两边的字母不同，我们就可以停止扩展，因为在这之后的子串都不能是回文串了。
         *
         * 时间复杂度：O(n^2)，其中 n 是字符串的长度。长度为 1和 2 的回文中心分别有 n和 n−1个，每个回文中心最多会向外扩展 O(n)次。
         *
         */
        int start = 0,end = 0;
        for (int i = 0;i<s.length();i++){
            int len1 = expendAroundCenter(s,i,i);
            int len2 = expendAroundCenter(s,i,i+1);
            int max = Math.max(len1,len2);
            if (max > (end - start)){
                start = i - (max-1)/2;
                end   = i+max/2;
            }
        }
        return s.substring(start,end);
    }
    private int expendAroundCenter(String s , int left,int right){
        while (left >= 0 && right<s.length()&& s.charAt(left) == s.charAt(right)){
            --left;
            ++right;
        }
        return right-left-1;
    }


    public String longestPalindrome1(String s) {
        //把相同的词汇或句子,在下文中调换位置或颠倒过来,产生首尾回环的情趣,叫做回文,也叫回环
        // 自己实现的算法，非常的暴力
        // abbba  abbba
        // abccba
        if(s == null){return null;}
        if ("".equals(s.trim()) || s.length() == 1) {
            return s;
        }

        char[] chars = s.toCharArray();
        String value = new String(new char[]{chars[0]});
        for (int i = 0;i<chars.length-1;i++){

            StringBuilder sb = new StringBuilder();
            sb.append(chars[i]);

            boolean flag = true;
            int last = i;
            for (int j = i+1;j<chars.length;j++){
                sb.append(chars[j]);
                if (chars[j] == chars[i]){
                    if (flag && last+1 != j){
                        flag = false;
                    }
                    last = j;
                    if (flag || isPalindrome(sb.toString())){
                        if (sb.length() > value.length()){
                            value = sb.toString();
                        }
                    }
                }
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
