package com.yaozou.algorithm.leetcode.string;

import java.util.ArrayList;
import java.util.List;

/**
 * created on 2020/9/28 17:57
 *
 * @author yaozou
 * @since v1.0.0
 */
public class ZConvert {

    /**
     * 6. Z 字形变换
     */
    public String convert(String s, int numRows) {
        if (numRows == 1){ return s;}

        List<StringBuilder> list = new ArrayList<>();
        for (int i = 0;i<Math.min(s.length(),numRows);i++){
            list.add(new StringBuilder());
        }

        // 是否换行
        boolean flag = false;
        int curr = 0;
        // 每个字母仅会出现一次
        // 当curr==0时或者curr==numRows-1时，换到下一行
        for (int i = 0;i<s.length();i++){
            list.get(curr).append(s.charAt(i));
            if (curr == 0 || curr == numRows-1 ){flag = !flag;}
            curr += flag?1:-1;
            System.out.println(i+"->"+flag+" "+curr+":"+s.charAt(i));
        }


        StringBuilder val = new StringBuilder();
        for (StringBuilder sb:list) {
            val.append(sb);
        }
        return val.toString();
    }

    public static void main(String[] args) {
        ZConvert convert = new ZConvert();
        System.out.println(convert.convert("ABCDEFGHIJKLMN",3));
    }
}
