package com.usc.se.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 饿汉式单例，记录当前交通拥挤数据的所属时间
 */
public class JamTime {
    static AtomicInteger atomicInteger = new AtomicInteger(0);

    private JamTime() {
        if (jamTime != null) {
            throw new RuntimeException("单例构造器禁止反射调用");
        }
    }
    private static JamTime jamTime = new JamTime();

    /**
     * 初始化时间，并返回0
     */
    public static int init() {
        JamTime.atomicInteger.set(0);
        return JamTime.atomicInteger.get();
    }

    /**
     * 设置最晚时间23
     */
    public static int last() {
        JamTime.atomicInteger.set(23);
        return JamTime.atomicInteger.get();
    }
    /**
     * 自增1，即后一个小时的数据
     */
    public static int add() {
        // 自增1
        JamTime.atomicInteger.incrementAndGet();
        return JamTime.atomicInteger.get();
    }

    /**
     * 自减1，即前一个小时的数据
     */
    public static int reduce() {
        JamTime.atomicInteger.decrementAndGet();
        return JamTime.atomicInteger.get();
    }
}
