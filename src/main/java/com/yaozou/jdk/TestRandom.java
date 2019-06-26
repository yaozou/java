package com.yaozou.jdk;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @Description:
 * @author: yaozou
 * @Date: 2019/6/26 17:52
 */
public class TestRandom {
    public static void main(String[] args){
        int max = 10000;
        int num1 = 0,num2 =0,num3=0;
        double large = 0.5093214159691332;
        double medium = 1.0186005706596197;
        double small = 1.2640544331803072;
        double[] rates = {0.1,0.15,0.2,0.3,0.4,0.5,0.6,0.65,0.8,0.9,1};
        for(int i = 0 ; i<max;i++){
            double random = rates[ThreadLocalRandom.current().nextInt(rates.length)];
            double rand = small*random;
            if(rand <= 0.5093214159691332){
                num1++;
            }else if(rand <=medium && rand > large){
                num2++;
            }else {
                num3++;
            }
        }

        System.out.println((double) num1/(double)max);
        System.out.println((double) num2/(double)max);
        System.out.println((double) num3/(double)max);
    }
}
