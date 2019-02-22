package com.yaozou.algorithm;

/**
 * @Description:
 * @author: yaozou
 * @Date: 2019/2/19 09:58
 */
public class TestSort {
    public static void main(String[] args){

    }

    class InsertSirt{
        /**
         * 直接插入排序
         * 基本思想：将当前无序区的第1个记录R[i]插入到有序区R[0...i-1]中适当的位置上，
         * 使R[0,...i]变为有序，这种方法通常称为增量法，因为它每次使有序区增加一个记录。
         */
        void insertSort(int[] R, int n){
            int i,j;
            int tmp;
            for(i=1;i<n;i++){
                tmp = R[i];
                j = i-1;
                while (j >= 0 && tmp < R[j]){
                    R[j+1] = R[j];
                    j--;
                }
                R[j+1] = tmp;
            }
        }

        /***
         * 二分插入排序
         * 基本思想： 设R[low,...high]是当前的查找区间，首先确定该区间的中点位置mid=[(low+hight)/2],
         * 然后将待查的k值与R[mid]比较：
         * (1)若R[mid]=k,则查找成功返回该位置
         * (2)若R[mid]>k,则由表的有序性可知R[mid..n-1]均大于k，因此若表中存在关键字等于k的记录，则该
         * 记录必须是在位置mid左边的子表R[0..mid-1]中，故新的查找区间是左子表R[0..mid-1]。
         * (3)若R[mid]<k,则要查找的k必在mid的右子表R[mid+1..n-1]中，即新的查找区间是右子表R[mid+1..n-1]。
         */
        void bInsertSort(int[] R, int n){
            int i,j,low,mid,high;
            int tmp;
            for(i = 1; i < n ; i++){
                tmp = R[i];
                j = i-1;
                low = 0; high = i-1;
                while (low <= high){
                    mid = (low+high)/2;
                    if (tmp < R[mid]){
                        high = mid-1;
                    }else {
                        low = mid+1;
                    }
                }
                for (;j >= high+1;j--){
                    R[j+1] = R[j];
                }
                R[high+1] = tmp;
            }
        }

        /**
         * 希尔排序
         * 基本思想：
         * （1）先取定一个小于n的整数d1作为第一个增量
         * （2）把表的全部记录分成d1个小组，所有距离为d1的倍数的记录放在同一个组中，在各组中进行直接插入排序
         * （3）然后取第二个增量d2(<d1),重复上述操作直至所取的增量dt=1为止。
         */
        void shellSort(int[] R, int n){
            int i,j,gap;
            int tmp;
            gap = n/2;
            while (gap > 0){
                for(i=gap;i<n;i++){
                    tmp = R[i];
                    j = i-gap;
                    while (j>=0 && tmp < R[j]){
                        R[j+gap] = R[j];
                        j = j-gap;
                    }
                    R[j+gap] = tmp;
                }
                gap = gap/2;
            }
        }
    }

    class ExchangeSort{
        /**
         * 冒泡排序
         * 基本思想：通过无序区中相邻记录关键字间的比较和位置的交换，使关键字最小的记录如气泡一般逐渐往上“漂浮”直至“水面”。
         */
        void bubbleSort(int[] R,int n){
            int i,j;
            int tmp;
            for(i=0;i<n-1;i++){
                for(j=n-1;j>i;j--){
                    if (R[j] < R[j-1] ){
                        tmp = R[j];
                        R[j] = R[j-1];
                        R[j-1] = tmp;
                    }
                }
            }
        }
        void bubbleSort1(int[] R,int n){
            int i,j,exchange;
            int tmp;
            for(i=0;i<n-1;i++){
                exchange = 0;
                for(j=n-1;j>i;j--){
                    if (R[j] < R[j-1] ){
                        tmp = R[j];
                        R[j] = R[j-1];
                        R[j-1] = tmp;
                        exchange = 1;
                    }
                }
                if (exchange == 0){
                    return;
                }
            }
        }

        /**
         * 快速排序
         * 基本思想：在待排序的n个记录中任取一个记录（通常第一个记录），
         * 把该记录放入适当位置后，数据序列被此记录划分成两部分。所有关键字比该记录关键字小的记录放置在一部分，
         * 所有比它大的记录放置在后一部分。
         * 并把记录排在这部分的中间（称为记录归位），这个过程称做一趟快速排序。
         */
        void quickSort(int[] R,int s,int t){
            int i=s,j=t;
            int tmp;
            if (s < t){
                tmp = R[s];
                while (i!=j){
                    while (j>i && R[j]>tmp){
                        j--;
                    }
                    R[i] = R[j];
                    while (j>i && R[i]<tmp){
                        i++;
                    }
                    R[j] = R[i];
                }
                R[i] = tmp;
                quickSort(R,s,i-1);
                quickSort(R,i+1,t);
            }
        }
    }

    class SelectSort{
        /**
         * 直接选择排序
         * 基本思想：
         *  第i趟排序开始时，当前有序区和无序区分别为R[0..i-1]和R[i..n-1]（n<=i<n-1）,
         *  该趟排序则是从当前无序区中选出关键字最小的记录R[k]，
         *  将它与无序区的第1个记录R[i]交换，使R[0..i]和R[i+1..n-1]分别变为新的有序区和新的无序区。
         *  因为每趟排序均使有序区中增加了一个记录，且有序区中的记录关键字均不大于无序中记录的关键字，
         *  即第i趟排序后R[0..i]的所有关键字小于等于R[i+1..n-1]中的所有关键字，
         *  所以进行n-1趟排序之后有R[0..n-2]的所有关键字小于等于R[n-1]也就是说，
         *  经过n-1趟排序之后，整个表R[0..n-1]递增有序。
         */
        void selectSort(int[] R,int n){
            int i,j,k;
            int tmp;
            for(i=0;i<n-1;i++){
                k = i;
                for(j=i+1;j<n;j++){
                    if (R[j] < R[k]){
                        k = j;
                    }
                }
                if (k!=i){
                    tmp = R[i];
                    R[i] = R[k];
                    R[k] = tmp;
                }
            }
        }

        class HeadSort{
            /** 初始建堆 大根堆*/
            void sift(int[] R,int low,int high){
                int i = low,j = 2*i;
                int tmp = R[i];
                while (j<=high){
                    if (j < high && R[j] < R[j+1]){
                        j++;
                    }
                    if (tmp < R[j]){
                        R[i] = R[j];
                        i = j;
                        j = 2*i;
                    }else {break;}
                }
                R[j] = tmp;
            }

            void heapSort(int[] R,int n){
                int i;
                int tmp;
                for(i = n/2;i>= 1;i--){
                    sift(R,i,n);
                }
                for(i = n; i>= 2;i--){
                    tmp = R[1];
                    R[1]= R[i];
                    R[i] = tmp;
                    sift(R,1,i-1);
                }
            }
        }

        /***
         * 归并排序
         */
        void merge(int[] R,int low,int mid,int high){
            int [] R1 = new int[(high-low)+1];
            int i = low,j = mid+1,k = 0;
            while (i <= mid && j <= high){
                if (R[i] <= R[j]){
                    R1[k] = R[i];
                    i++;j++;
                }else{
                    R1[k] = R[j];
                    j++;k++;
                }
            }
            while (i <= mid){
                R1[k] = R[i];
                i++;j++;
            }
            while (j <= high){
                R1[k] = R[j];
                j++;k++;
            }
            for(k=0,i=low;i<=high;k++,i++){
                R[i] = R1[k];
            }
        }
        void mergePass(int[] R,int length,int n){
            int i;
            for(i=0;i+2*length-1<n;i=i+2*length){
                merge(R,i,i+length-1,i+2*length-1);
            }
            if (i+length-1<n){
                merge(R,i,i+length-1,n-1);
            }
        }

    }
}

