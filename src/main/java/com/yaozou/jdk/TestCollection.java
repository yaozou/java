package com.yaozou.jdk;/**
 * created by yaozou on 2018/4/20
 */

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * 集合类
 * @author yaozou
 * @create 2018-04-20 17:47
 **/
public class TestCollection {

    public void testMap(){
       Map hash = new HashMap();
       hash.put("aa","aaa");
       hash.put(null,null);

       Map table = new Hashtable();
       table.put("aa","aaa");
       table.put(null,null);

    }
}
