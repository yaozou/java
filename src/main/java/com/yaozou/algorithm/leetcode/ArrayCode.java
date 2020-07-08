package com.yaozou.algorithm.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 数组
 * @author: yaozou
 * @Date: 2019/3/15 15:02
 */
public class ArrayCode {

    public static void main(String[] args) {
        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        System.out.println(isValidSudoku(board));
    }
    public static int[][] rotate(int[][] matrix) {
        // 1。将每一列的数据进行倒转
        int length = matrix.length , tmp;
        for(int i = 0; i < length ; i++){
            for(int j = length-1;j>=length/2;j--){
                tmp = matrix[length-1-j][i];
                matrix[length-1-j][i] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }

        // 2、将i,j位置的数据与j,i交换
        for(int i = 0; i < length ; i++){
            for(int j = i ; j < matrix[i].length ; j++){
                if(i != j ){
                    tmp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = tmp;
                }
            }
        }
        return matrix;
    }

    public static boolean isValidSudoku(char[][] board) {
        // 初始化值
        HashMap<Integer, Integer>[] rows = new HashMap[9];
        HashMap<Integer, Integer> [] columns = new HashMap[9];
        HashMap<Integer, Integer> [] boxes = new HashMap[9];
        for (int i = 0; i < 9; i++) {
            rows[i] = new HashMap<>(9);
            columns[i] = new HashMap<>(9);
            boxes[i] = new HashMap<>(9);
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char num = board[i][j];
                if (num != '.') {
                    int n = (int)num;
                    //得到所在宫格
                    int box_index = (i / 3 ) * 3 + j / 3;

                    // 保存当前值
                    rows[i].put(n, rows[i].getOrDefault(n, 0) + 1);
                    columns[j].put(n, columns[j].getOrDefault(n, 0) + 1);
                    boxes[box_index].put(n, boxes[box_index].getOrDefault(n, 0) + 1);

                    // 是否已经存在
                    if (rows[i].get(n) > 1 || columns[j].get(n) > 1 || boxes[box_index].get(n) > 1)
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        Map<Integer,Integer> map = new HashMap<>(nums.length);
        for(int i = 0;i < nums.length;i++){
            if(map.containsKey(nums[i])){
                result[0] = map.get(nums[i]);
                result[1] = i;
                return result;
            }
            map.put(target-nums[i],i);
        }
        return result;
    }

    public static int[] moveZeroes(int[] nums) {
        int zeroNum = 0;
        int length = nums.length;
        for(int i = length-1;i>=0;i--){
            if(nums[i] == 0){
                for(int j = i; j<length-zeroNum-1;j++ ){
                    nums[j] = nums[j+1];
                }
                nums[length-1-zeroNum] = 0;
                zeroNum += 1;
            }
        }
        return nums;
    }

    public static int[] plusOne(int[] digits) {
        int dis = 0 , num;
        boolean flag = true;
        for(int i = digits.length-1;i>=0;i--){
            num = dis + digits[i];
            if(flag){
                num += 1;
                flag = false;
            }
            dis = 0;
            if(num > 9){
                dis = num-9;
                num = num - 10;
            }
            digits[i] = num;
        }
        if(dis > 0){
            int[] newDigits = new int[digits.length+1];
            newDigits[0] = dis;
            for (int i = 0;i<digits.length;i++){
                newDigits[i+1] = digits[i];
            }
            return newDigits;
        }
        return digits;
    }

    public static int[] intersect(int[] nums1, int[] nums2) {
        // 如连连看游戏 连成一对的就消除
        int k = 0,size = nums1.length > nums2.length ? nums2.length:nums1.length;
        int[] tmp = new int[size];
        int[] minNum = nums1.length > nums2.length ? nums2:nums1;
        int[] maxNum = nums1.length <= nums2.length ? nums2:nums1;
        int length = maxNum.length;
        boolean flag;
        for(int i=0;i<minNum.length;i++){
            flag = false;
            for(int j = 0; j < length;j++){
                if(!flag && minNum[i] == maxNum[j]){
                    flag = true;
                    tmp[k] = minNum[i];
                    k++;
                }
                if(flag && j+1 < length){
                    maxNum[j] = maxNum[j+1];
                }
            }
            if(flag){
                length--;
            }
        }

        int[] result = new int[k];
        System.arraycopy(tmp, 0, result, 0, k);
        return result;
    }

    public static int singleNumber(int[] nums) {
        int tmp = 0;
        for(int i=0;i<nums.length;i++){
            tmp ^= nums[i];
        }
        return tmp;
    }
    public static boolean containsDuplicate(int[] nums) {
        int length = nums.length;
        Map<Integer,Integer> map = new HashMap<>(nums.length);
        for(int i=0;i<length;i++){
            if(map.containsKey(nums[i])){
                return true;
            }
            map.put(nums[i],i);
        }
        return false;
    }
    public static void rotate(int[] nums, int k) {
        int length = nums.length;
        if(k >= length){
            k %= length;
        }
        int tmp = nums[0],nextTmp,next,j=0,first=0;
        for (int i = 0; i < length; i++) {
            next = (j+k)%length;
            nextTmp = nums[next];
            nums[next] = tmp;
            tmp = nextTmp;
            // 下一次移j位数
            j = next;
            if((k != 0 && length % k == 0) || k > length/2){
                if (next==first) {
                    j++;
                    tmp=nums[j];
                    first=j;
                }
            }
        }
        print(nums);
    }

    public static int maxProfit(int[] prices) {
        int maxProfit = 0;
        int in = 0;
        boolean buyFlag = false;
        for(int i = 0;i<prices.length;i++){
            if(!buyFlag){
                if((i+1)<prices.length && prices[i] < prices[i+1]){
                    in = prices[i];
                    buyFlag = true;
                }
            }else{
                if((i == prices.length-1) || (in < prices[i] && prices[i] > prices[i+1])){
                    maxProfit = maxProfit+prices[i]-in;
                    buyFlag = false;
                }
            }
        }
        return maxProfit;
    }

    private static int removeDuplicates(int[] nums) {
        int size = nums.length;
        int flagT=0;
        for (int i = 0;i < nums.length-1; i++){
            if(nums[i] == nums[i+1]){
                flagT++;
            }else{
                int index = i-flagT+1;
                nums[index]=nums[i+1];
            }
        }
        size = size-flagT;
        int[] num2 = Arrays.copyOf(nums,size);
        nums = num2;
        print(nums);
        return size;
    }

    private static void print(int[] nums){
        for (int i = 0;i < nums.length; i++){
            System.out.print(nums[i]);
            System.out.print(" ");
        }
        System.out.println();
    }

    private static void print(int[][] nums){
        for (int i = 0;i < nums.length; i++){
            print(nums[i]);
        }
        System.out.println();
    }
}
