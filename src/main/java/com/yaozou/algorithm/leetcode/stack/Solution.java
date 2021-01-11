package com.yaozou.algorithm.leetcode.stack;

import java.util.*;

/**
 * created on 2020/7/30 14:10
 *
 * @author yaozou
 * @since v1.0.0
 */
public class Solution {
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

    /**
     * 接雨水
     */
    public int trap(int[] height) {
        if (height.length == 0){return 0;}
        int num = 0,size = height.length;
        for (int i =1;i<size-1;i++){
            int maxLeft = 0,maxRight=0;
            for (int j =i;j>=0;j--){
                maxLeft = Math.max(maxLeft,height[j]);
            }
            for (int j = i;j<size;j++){
                maxRight = Math.max(maxRight,height[j]);
            }
            num += Math.min(maxLeft,maxRight)-height[i];
        }
        return num;
    }
    public int trap2(int[] height) {
        if (height == null || height.length == 0){return 0;}
        int num = 0,size = height.length;

        int[] leftMax = new int[size];
        leftMax[0] = height[0];
        for (int i = 1;i<size;i++){
            leftMax[i] = Math.max(height[i],leftMax[i-1]);
        }

        int[] rightMax = new int[size];
        rightMax[size - 1] = height[size-1];
        for (int i = size-2;i >= 0;i--){
            rightMax[i] = Math.max(height[i],rightMax[i+1]);
        }

        for (int i = 1;i<size-1;i++){
            num += Math.min(leftMax[i],rightMax[i])-height[i];
        }

        return num;
    }

    public int trap3(int[] height) {
        int num = 0,current = 0;
        Deque<Integer> stack = new LinkedList<>();
        while (current < height.length){
            while (!stack.isEmpty() && height[current] > height[stack.peek()]){
                int top = stack.pop();
                if (stack.isEmpty()){break;}
                int distance = current - stack.peek()-1;
                int boundedHeight = Math.min(height[current],height[stack.peek()])-height[top];
                num += distance * boundedHeight;
            }
            stack.push(current++);
        }

        return num;
    }

    /** 简化路径 */
    public String simplifyPath(String path) {
        if (path == null){return null;}
        String[] folders = path.split("/");
        // 一个.表示当前目录本身；两个.表示将目录切换到上一级（指向父目录）
        String point = ".",doublePoint = "..";
        Deque<String> stack = new LinkedList<>();
        for (String f:folders){
            if ("".equals(f) || point.equals(f)){
                continue;
            }
            if (doublePoint.equals(f)){
                if (!stack.isEmpty()){stack.poll();}
            }else{
                stack.push(f);
            }
        }
        StringBuilder sb = new StringBuilder();
        if (stack.isEmpty()){sb.append("/");}
        while (!stack.isEmpty()){
            sb.append("/").append(stack.pollLast());
        }
        return sb.toString();
    }

    /** 柱状图中最大的矩形 */
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0){return 0;}
        int size = heights.length;
        int[] left = new int[size];
        int[] right = new int[size];

        Stack<Integer> stack = new Stack<>();
        for (int i = 0;i<size;i++){
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]){
                stack.pop();
            }
            left[i] = stack.isEmpty()?-1:stack.peek();
            stack.push(i);
        }

        stack.clear();
        for (int i = size-1;i>=0;i--){
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]){
                stack.pop();
            }
            right[i] = stack.isEmpty()?size:stack.peek();
            stack.push(i);
        }

        int maxArea = 0;
        for (int i = 0;i<size;i++){
            maxArea = Math.max(maxArea,(right[i]-left[i]-1)*heights[i]);
        }
        return maxArea;
    }

    /** 下一个更大元素 I */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer,Integer> nums = new HashMap<>(nums2.length);
        Stack<Integer>       stack = new Stack<>();
        for (int i = 0;i<nums2.length;i++){
            while (!stack.isEmpty() && nums2[i] > stack.peek()){
                nums.put(stack.pop(),nums2[i]);
            }
            stack.add(nums2[i]);
        }

        int[] nums3 = new int[nums1.length];
        for (int i = 0;i<nums1.length;i++ ){
            nums3[i] = nums.getOrDefault(nums1[i],-1);
        }
        return nums3;
    }

    /** 棒球比赛 */
    public int calPoints(String[] ops) {
        Stack<Integer> stack = new Stack<>();
        for (String op:ops){
            if ("C".equals(op)){
                stack.pop();//表示前一次得分无效，将其从记录中移除
            }else if ("D".equals(op)){
                stack.add(stack.peek()*2);
            }else if ("+".equals(op)){
                int[] grades = new int[2];
                int i  = 0;
                while (!stack.isEmpty()){
                    grades[i] = stack.pop();
                    i++;
                    if (i == 2){break;}
                }
                stack.add(grades[1]);
                stack.add(grades[0]);
                stack.add(grades[0]+grades[1]);
            }else{
                stack.add(new Integer(op));
            }
        }

        int sum = 0;
        while (!stack.isEmpty()){
            sum += stack.pop();
        }
        return sum;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.calPoints(new String[]{"5","-2","4","C","D","9","+","+"}));
    }
}
