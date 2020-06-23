package com.yaozou.model.behavior;

import java.math.BigDecimal;

/**
 * @author yaoozu
 * chain of responsibility patter
 * @date 2020/6/2318:11
 * @since v1.0.0
 */
public class ChainOfResponsibilityMethod {
    public static void main(String[] args) {
        BaseHandler handler1 = new ProjectManager();

        handler1.handleRequest("Tom",new BigDecimal(200));

        handler1.handleRequest("Tom",new BigDecimal(500));

        handler1.handleRequest("Tom",new BigDecimal(1000));
    }
}
abstract class BaseHandler{
    /**
     * 示意处理请求的方法
     */
    public abstract void handleRequest(String name , BigDecimal fee);

    public String msg(String name , BigDecimal fee,String msg){
        return name+" "+fee.toPlainString()+"->"+msg;
    }
}

class ProjectManager extends BaseHandler{
    @Override
    public void handleRequest(String name , BigDecimal fee) {
        /**
         * 判断是否有后继的责任对象
         * 如果有，就转发请求给后继的责任对象
         * 如果没有，则处理请求
         */
        if (fee.compareTo(new BigDecimal(200)) > 0) {
            System.out.println(msg(name,fee,"项目已审批，下一步部门经理审批"));
            new DeptManager().handleRequest(name,fee);
        } else {
            System.out.println(msg(name,fee,"审批通过"));
        }
    }
}

class DeptManager extends BaseHandler{

    @Override
    public void handleRequest(String name, BigDecimal fee) {
        if (fee.compareTo(new BigDecimal(500)) > 0) {
            System.out.println(msg(name,fee,"经理审批已审批，下一步总经理审批"));
            new GeneralManager().handleRequest(name,fee);
        } else {
            System.out.println(msg(name,fee,"审批通过"));
        }
    }
}

class GeneralManager extends BaseHandler{

    @Override
    public void handleRequest(String name, BigDecimal fee) {
        System.out.println(msg(name,fee,"总经理已审批"));
        System.out.println(msg(name,fee,"审批通过"));
    }
}


