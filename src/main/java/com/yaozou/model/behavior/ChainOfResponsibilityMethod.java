package com.yaozou.model.behavior;

import java.math.BigDecimal;

/**
 * @author yaoozu
 * chain of responsibility patter
 * <b>
 *     责任链模式是一种对象的行为模式。在责任链模式里，很对对象由每一个对象对其下家的引用而连接起来形成一条链。
 *     请求在这个链上传递，直到链上的某一个对象决定处理此请求。
 *     发出这个请求的客户端并不知道链上的哪一个对象是最终处理这个请求，这使得系统可以在不影响客户端的情况下动态地重新组织和分配责任。
 * </b>
 * <b>击鼓传花便是责任链模式的应用。责任链可能是一条直线、一个环链或者一个树结构的一部分。</b>
 *
 * <b>责任链模式涉及到的角色如下所示：</b>
 * <ul>
 *     <li>
 *         抽象处理者（Handler）角色：定义出一个处理请求的接口。如果需要，接口可以定义出一个方法以设定和返回对下家的引用。
 *          这个角色通常由一个Java抽象类或者Java接口实现。Handler类的聚合关系给出了具体子类对下家的引用，抽象方法handleRequest()规范了子类处理请求的操作。
 *     </li>
 *     <li>
 *         具体处理者（ConcreteHandler）角色：具体处理者接到请求后，可以选择将请求处理掉，或者将请求传给下家。
 *         由于具体处理者持有对下家的引用，因此，如果需要，具体处理者可以访问下家。
 *     </li>
 * </ul>
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


