package com.yaozou.db.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Pool;

public class TestRedisBasic {
    public static void main(String[] args){
        /*Jedis jedis = new Jedis("192.168.98.81",6379);
        jedis.auth("163@.com");*/

        Pool<Jedis> jedisPool =new JedisPool(new GenericObjectPoolConfig(), "192.168.98.81",6379, 2000, "163@.com", 0, null, false);
        Jedis jedis = jedisPool.getResource();
    }
    public void list(Jedis jedis){
        jedis.select(0);
        String key = "list";
        jedis.lpush(key,"111");
        jedis.lpush(key,"222");

        jedis.lpop(key);
    }
}
