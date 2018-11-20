package com.yaozou.enums;

/**
 * @Description:
 * @author: yaozou
 * @Date: 2018/11/1 10:26
 */
public enum CommonEnum {
    DN_OPEN_DOOR(0x1,0x1,"远程打开车门锁"),
    DN_CLOSE_DOOR(0x1,0x2,"远程关闭车门锁"),

    UP_RESULT(0x81,0,"远程控制结果上报"),
    UP_PARAM(0x82,0x3,"参数上报"),
    ;

    CommonEnum(int serviceCode,int funCode,String msg){
        this.serviceCode = serviceCode;
        this.funCode = funCode;
        this.msg = msg;
    }

    private int serviceCode;
    private int funCode;
    private String msg;

    public int getFunCode() {
        return funCode;
    }

    public void setFunCode(int funCode) {
        this.funCode = funCode;
    }

    public void setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
