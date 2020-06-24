package com.yaozou.pattern.creation;/**
 * created by yaozou on 2018/4/26
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 多例模式
 * 在一个解决方案中结合两个或多个模式，以解决一般或重复发生的问题
 * @author yaozou
 * @create 2018-04-26 21:36
 **/
public class MultitudeMethod {
    private static int num = 3; //实例个数
    private static Map<Integer,MultitudeMethod> multitude = new HashMap<Integer, MultitudeMethod>(); //存放实例
    private static int index; //当前实例所在位置
    static{ //当类加载时创建指定个数的实例
        for(int i = 0;i < num;i++){
            multitude.put(i,new MultitudeMethod());
        }
    }
    private MultitudeMethod(){}
    public static MultitudeMethod getInstance(){
        Random random = new Random();
        index = random.nextInt(num); //随机获取实例
        return multitude.get(index);
    }
    public int getIndex() {
        return index;
    }
    public static void main(String[] args) {
        System.out.println("当前实例是第"+MultitudeMethod.getInstance().getIndex());
        System.out.println("当前实例是第"+MultitudeMethod.getInstance().getIndex());
        System.out.println("当前实例是第"+MultitudeMethod.getInstance().getIndex());
    }
}
