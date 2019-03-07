package com.yaozou.spring.service;

import com.yaozou.Dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {
    @Autowired
    private TestDao testDao;

    /**
     * 隔离级别
     * Isolation.READ_UNCOMMITTED 一个事务可以读取别一个未提交事务的数据
     * Isolation.READ_COMMITTED 一个事务要等别一个事务提交后台可能读取数据 （这就是读提交，若有事务对数据进行更新（UPDATE）操作时，读操作事务要等待这个更新操作事务提交后才能读取数据，可以解决脏读问题。但不可解决不可重复读。）
     * Isolation.REPEATABLE_READ 在开始读取数据（事务开启）时，不再允许修改操作 （可以解决不可重复读问题，不可重复读对应的是修改，即UPDATE操作。但是可能还会有幻读问题。因为幻读问题对应的是插入INSERT操作，而不是UPDATE操作。）
     * Isolation.SERIALIZABLE 事务串行化顺序执行，可以避免脏读，不可重复读与幻读
     */
    @Transactional(readOnly = true,isolation = Isolation.SERIALIZABLE)
    public void query(){
        System.out.println("This is query.");
    }

    public void query1(){
        System.out.println("This is query.");
    }

    public void updateAndQuery(){
        query1();
        update();
    }

    @Transactional(readOnly = false)
    public void update(){
        System.out.println("This is update.");
        delete();
    }

    public void delete(){ // 1、抛出异常 2、try-catch异常
        System.out.println("This is delete.");
    }
}
