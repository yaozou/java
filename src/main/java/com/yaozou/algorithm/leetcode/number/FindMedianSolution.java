package com.yaozou.algorithm.leetcode.number;

/**
 * created on 2020/9/27 11:00
 *
 * @author yaozou
 * @since v1.0.0
 */
public class FindMedianSolution {
    public static void main(String[] args) {
        FindMedianSolution f = new FindMedianSolution();
        System.out.println(f.findMedianSortedArrays(new int[]{1, 3},new int[]{2}));
        System.out.println(f.findMedianSortedArrays(new int[]{},new int[]{1,3, 4}));
        System.out.println(f.findMedianSortedArrays(new int[]{1,2},new int[]{-1,3}));
    }

    /**
     * 4.寻找两个正序数组的中位数
     * 给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。
     * 请你找出这两个正序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
     * 你可以假设 nums1 和 nums2 不会同时为空。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/median-of-two-sorted-arrays
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        // 中位数，又称中点数，中值。中位数是按顺序排列的一组数据中居于中间位置的数，即在这组数据中，有一半的数据比他大，有一半的数据比他小
        // 出这两个正序数组的中位数

        if (nums1.length == 0&& nums2.length == 0){
            return 0;
        }

        // 将两个数组数进行合并并排序
        int[] array;
        if (nums2.length == 0 || (nums1.length > 0 && nums1[0] < nums2[0])){
            array = array(nums1,nums2);
        }else{
            array = array(nums2,nums1);
        }

        if (array.length == 1){
            return array[0];
        }

        if (array.length % 2 > 0){
            // 奇数
            return array[array.length/2];
        }else{
            // 偶数
            int mod = array.length/2;
            double d1 = array[mod];
            double d2 = array[mod-1];
            return (d1+d2)/2;
        }
    }

    private int[] array(int[] nums1 , int[] nums2){
        int length1 = nums1.length;
        int length2 = nums2.length;

        int[] nums = new int[length1+length2];
        int j = 0;
        int m = 0;
        for (int i = 0;i<length1;i++){
            int a = nums1[i];
            for (;j<length2;j++){
                int b = nums2[j];
                if (b<=a){
                    nums[m] = b;
                    m++;
                }else{
                    break;
                }
            }
            nums[m] = a;
            m++;
        }

        for (;j<length2;j++){
            nums[m] = nums2[j];
            m++;
        }

        return nums;
    }
}
