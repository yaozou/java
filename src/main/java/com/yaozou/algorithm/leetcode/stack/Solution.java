package com.yaozou.algorithm.leetcode.stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * created on 2020/7/30 14:10
 *
 * @author yaozou
 * @since v1.0.0
 */
public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        // "()[]{}"
        System.out.println(solution.isValid("()[]{}"));
        // "(()("
        System.out.println(solution.isValid("(()("));
        // "{{)}"
        System.out.println(solution.isValid("{{)}"));

    }
    public boolean isValid(String s) {
        if (s == null || "".equals(s)){
            return true;
        }
        if (s.length() % 2 != 0){return false;}
        Map<Character,Character> map = getSymMap();
        char[] chars = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        boolean flag = false;
        boolean first = true;
        for (int i = 0;i<chars.length;i++){
            char charV = chars[i];
            Character symChar = map.get(charV);
            if (symChar != null){
                if (first){first = false;flag=true;}
                boolean sym = (!stack.isEmpty() && (stack.pop() == symChar.charValue()));
                flag &= sym;
            }
            if (symChar == null){ stack.push(charV);}
        }
        flag &= stack.isEmpty();
        return flag;
    }

    private Map<Character,Character> getSymMap(){
        char[] brackets = {'(',')','{','}','[',']'};
        Map<Character,Character> map = new HashMap<>(brackets.length);
        for (int i = 0;i<brackets.length;i=i+2){
            char val = brackets[i];
            int index = i+1;
            map.put(brackets[index],val);
        }
        return map;
    }

    public int[] dailyTemperatures(int[] T) {
        int[] tmps = new int[T.length];

        return tmps;
    }
}
