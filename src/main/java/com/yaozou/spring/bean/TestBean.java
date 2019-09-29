package com.yaozou.spring.bean;

import lombok.Data;

@Data
public class TestBean {
    private String testName;
    private int    num;

    @Override
    public String toString(){
        return testName+" "+num;
    }
}
