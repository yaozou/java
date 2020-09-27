package com.yaozou.algorithm.leetcode.linked;

/**
 * created on 2020/9/27 11:12
 *
 * @author yaozou
 * @since v1.0.0
 */
public class MinOperations {

    public static void main(String[] args) {
        MinOperations o = new MinOperations();

        System.out.println(o.minOperations(new String[]{"d1/","d2/","./","d3/","../","d31/"}));
        System.out.println(o.minOperations(new String[]{"d1/","d2/","../","d21/","./"}));
        System.out.println(o.minOperations(new String[]{"d1/","../","../","../"}));
    }

    /**
     * 文件夹操作日志收集器
     * @param logs
     * @return
     */
    public int minOperations(String[] logs){
        if (logs == null || logs.length == 0){return 0;}
        int num = 0;
        // 如果在父文件夹中，停留在当前文件夹
        String last = "../";
         // 继续停留在当前文件夹
        String current = "./";
        for (int i= 0;i<logs.length;i++) {
            if (last.equals(logs[i])){
                num = num == 0?num:num-1;
            }else if (current.equals(logs[i])){
                continue;
            }else{
                num++;
            }
        }
        return num;
    }
}
