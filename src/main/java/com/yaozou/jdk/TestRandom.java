package com.yaozou.jdk;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description:
 * @author: yaozou
 * @Date: 2019/6/26 17:52
 */
public class TestRandom {
    public static void main(String[] args){
        List<BigDecimal> list = new ArrayList(3);
        list.add(new BigDecimal(3.24));
        list.add(new BigDecimal(2.44));
        list.add(new BigDecimal(2.24));

        BigDecimal weightTotal = BigDecimal.ZERO;
        for(BigDecimal val : list){
            weightTotal = weightTotal.add(val);
        }

        BigDecimal weightLongest = BigDecimal.ZERO;
        List<Double> finalWeights = new ArrayList<Double>();
        StringBuilder sb = new StringBuilder();
        for(BigDecimal val : list){
            BigDecimal weight = weightTotal.subtract(val);
            weightLongest = weightLongest.add(weight);
            finalWeights.add(weightLongest.doubleValue());
            sb.append(weightLongest.doubleValue()+" ");
        }
        System.out.println(sb.toString());
    }
}
