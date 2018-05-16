package com.yaozou.jdk;/**
 * created by yaozou on 2018/5/16
 */

/**
 * Cloneable接口
 * @author yaozou
 * @create 2018-05-16 11:00
 **/
public class TestCloneable implements  Cloneable{
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
