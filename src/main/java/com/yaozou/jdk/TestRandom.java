package com.yaozou.jdk;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Description:
 * @author: yaozou
 * @Date: 2019/6/26 17:52
 */
public class TestRandom {
    private static Random rng = new Random(2019);
    public static void main(String[] args){
        long max = 150;
        int i=0,j = 0;
        while (i < 1000){
            i++;
            long rtt = nextRTT();
            if(max <= rtt){
                j++;
            }
        }

        System.out.println("----"+j);
    }

    private static long nextRTT() {
        double u = rng.nextDouble();
        int x = 0;
        double cdf = 0;
        while (u >= cdf) {
            x++;
            cdf = 1 - Math.exp(-1.0D * 1 / 50 * x);
        }
        System.out.println(x+":"+u);
        return x;
    }
}
