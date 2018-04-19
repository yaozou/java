package com.yaozou.model.creation;

import java.io.*;

/**
 * 原型模式
 * 将一个对象作为原型，对其进行复制、克隆，产生一个和原对象类似的新对象
 * 1、浅复制：将一个对象复制后，基本数据类型的变量都会重新创建，而引用类型，指向的还是原对象所指向的。
   2、深复制：将一个对象复制后，不论是基本数据类型还有引用类型，都是重新创建的。简单来说，
      就是深复制进行了完全彻底的复制，而浅复制不彻底。（
      要实现深复制，需要采用流的形式读入当前对象的二进制输入，再写出二进制数据对应的对象。）
 */
public class PrototypeMethod implements Cloneable,Serializable {
    /**浅复制*/
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public Object deepClone() throws ClassNotFoundException,IOException{
        /*写入当前对象的二进制流*/
        ByteArrayOutputStream  bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);

        /*读出二进制流产生新对象*/
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }
}
