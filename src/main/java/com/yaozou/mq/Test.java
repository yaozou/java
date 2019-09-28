package com.yaozou.mq;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @Description: TODO
 * @Author yao.zou
 * @Date 2019/9/3 0003
 * @Version V1.0
 **/
public class Test {
    public static void main(String[] args) throws IOException {
        String name = "com.yaozou.mq";
        String resourceName = "/"+name.replaceAll("\\.","/");
        System.out.println(resourceName);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource[] resources = resolver.getResources(resourceName);

        for (Resource resource : resources) {
            System.out.print(resource.getURL());
        }
    }
}
