package com.yaozou.db.redis;

import redis.clients.jedis.Jedis;

public class TestRedisBasic {

    public void list(Jedis jedis){
        String key = "list";
        jedis.lpush(key,"111");
        jedis.lpush(key,"222");
    }
}
