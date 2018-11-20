package com.yaozou.enums;

/**
 * @Description:
 * @author: yaozou
 * @Date: 2018/11/12 14:13
 */
public class TestEnum {
    public static void main(String[] args){
        CommonEnum commonEnum = CommonEnum.valueOf("DN_OPEN_DOOR");
        System.out.println(commonEnum.getFunCode());
    }
}
