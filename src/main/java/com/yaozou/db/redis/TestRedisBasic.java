package com.yaozou.db.redis;

import redis.clients.jedis.Jedis;

public class TestRedisBasic {

    public void list(Jedis jedis){
        String key = "list";
        jedis.lpush(key,"111");
        jedis.lpush(key,"222");

        jedis.lpop(key);
    }

    public static void main(String[] args){
        int[] vals = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        for (int val:vals) {
            System.out.println((val%15));
        }
    }
}
