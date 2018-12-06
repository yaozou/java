package com.yaozou.jdk;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @Description:
 * @author: yaozou
 * @Date: 2018/12/6 10:51
 */
public class TestNumber {
    public void testDecimal(){
        DecimalFormat decimalFormat = new DecimalFormat("###################.##");
        BigDecimal p2 = new BigDecimal(2.0D);
        BigDecimal p3 = new BigDecimal(2.00D);
        BigDecimal p4 = new BigDecimal(2.50D);
        BigDecimal p5 = new BigDecimal(2.585444D);
        System.out.println(p2.toString());
        System.out.println(p3.toString());
        System.out.println(p4.toString());
        System.out.println(p5.toString());

        System.out.println(decimalFormat.format(p2));
        System.out.println(decimalFormat.format(p3));
        System.out.println(decimalFormat.format(p4));
        System.out.println(decimalFormat.format(p5));
    }
}
