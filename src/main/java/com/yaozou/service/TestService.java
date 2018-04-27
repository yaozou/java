package com.yaozou.service;

import com.yaozou.Dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {
    @Autowired
    private TestDao testDao;

    @Transactional(readOnly = true)
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
