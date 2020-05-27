package com.usc.se.util;

import redis.clients.jedis.Jedis;

/**
 * 饿汉式单例Jedis
 */
public class JedisConnect {
    static Jedis jedis = new Jedis("121.199.31.199", 6379);
    private JedisConnect() {
        if (jedisConnect != null) {
            throw new RuntimeException("单例构造器禁止反射调用");
        }
    }
    private static JedisConnect jedisConnect = new JedisConnect();

    public static Jedis getJedis() {
        return jedisConnect.jedis;
    }
}
