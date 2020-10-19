package com.yaozou.algorithm.leetcode.number;

import java.util.HashMap;
import java.util.Map;

/**
 * created on 2020/10/19 16:38
 *
 * @author yaozou
 * @since v1.0.0
 */
public class Simple {

    /**
     * 13.回文数字转整数
     */
    public int romanToInt(String s) {
        if (s == null || "".equals(s.trim())){return 0;}
        int num = 0;
        char[] chars = s.toCharArray();
        int preNum = getValue(chars[0]);
        for (int i = 1;i<chars.length;i++){
            int val = getValue(chars[i]);
            if (val > preNum){
                num -= preNum;
            }else {
                num += preNum;
            }
            preNum = val;
        }
        num += preNum;
        return num;
    }
    private int getValue(char c){
        switch (c){
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }
    public int romanToInt1(String s) {
        if (s == null || "".equals(s.trim())){return 0;}
        Map<Character,Integer> map = new HashMap<>(8);
        map.put('I',1);map.put('V',5);map.put('X',10);
        map.put('L',50);map.put('C',100);map.put('D',500);map.put('M',1000);

        /*
         *  I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
         *  X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
         *  C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
         */
        Map<String,Integer> strMap = new HashMap<>(8);
        strMap.put("IV",4);strMap.put("IX",9);strMap.put("XL",40);
        strMap.put("XC",90);strMap.put("CD",400);strMap.put("CM",900);

        int num = 0;
        char[] chars = s.toCharArray();
        // IV--4  IX--9 XLIX--49 CMXCIX--999

        int lastNum = 0;
        int next;
        for (int i = 0;i<chars.length;i+=next) {
            int curr = map.get(chars[i]);
            next = 1;
            if (i+1 < chars.length){
                char nextChar = chars[i+1];
                Integer val = strMap.get(str(chars[i],nextChar));
                if (val != null){
                    curr = val;
                    next = 2;
                }
            }
            num += lastNum;
            lastNum = curr;
        }
        num += lastNum;
        return num;
    }

    private String str(char char1,char char2){
        StringBuilder sb = new StringBuilder();
        sb.append(char1).append(char2);
        return sb.toString();
    }


    public static void main(String[] args) {
        Simple s = new Simple();
        System.out.println(s.romanToInt("MCMXCIV"));
    }

}
