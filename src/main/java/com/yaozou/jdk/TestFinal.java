package com.yaozou.jdk;/**
 * created by yaozou on 2018/5/3
 */

/**
 *
 * @author yaozou
 * @create 2018-05-03 21:04
 **/
public class TestFinal {
    int i;
    public static void main(String[] args){
        StringBuffer sb1 = new StringBuffer();
        sb1.toString();
        StringBuilder sb2 = new StringBuilder();

        String a = "hello2";
        final String b = "hello"; //编译器会把它当做编译期常量使用(能确切知道final变量值的情况下)
        final String b1 = getHello();
        String d = "hello";
        String c = b + 2;
        String c1 = b1 + 2;
        String e = d + 2;
        System.out.println((a == c)); // true
        System.out.println((a == c1)); // false
        System.out.println((a == e)); // false
    }

    private static String getHello() {
        return "hello";
    }

    public static void testClass(final TestFinal testFinal){ //引用变量被final修饰之后，虽然不能再指向其他对象，但是它指向的对象的内容是可变的。
        testFinal.i++;
    }

    public static void changeVal(final int i){
       // i++; // 不可修改
    }

}
