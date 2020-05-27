package com.usc.se.service;

import com.usc.se.util.JedisConnect;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 3、驻留地分析
 */
@Service
public class PlaceService {

    // 1、驻留地的数据
    static ArrayList<HashMap<String, Double[]>> residentList = new ArrayList<>();
    // 2、工作地的数据
    static ArrayList<HashMap<String, Double[]>> workList = new ArrayList<>();
    // 3、居住地的数据
    static ArrayList<HashMap<String, Double[]>> liveList = new ArrayList<>();

    // 1、获取驻留地的经纬度
    public ArrayList<HashMap<String, Double[]>> getResident() {
        return residentList;
    }

    // 2、获取工作地的经纬度
    public ArrayList<HashMap<String, Double[]>> getWork() {
        return workList;
    }

    // 3、获取居住地的经纬度
    public ArrayList<HashMap<String, Double[]>> getLive() {
        return liveList;
    }

    // 1、驻留地数据初始化
    // @Scheduled(fixedRate = 999999999)
    public void initResident() {
        // 获取Jedis对象
        Jedis jedis = JedisConnect.getJedis();
        // 默认是数据库0，调用这个函数代表切换为数据库1,之后记得重置为0
        jedis.select(1);
        // 获取所有key
        Set<String> allKey = jedis.keys("lingering:*");
        // 存储每个驻留地的经纬度
        HashSet<String> hashSet = new HashSet<>();
        for (String string : allKey) {
            String[] strings = string.split(":");
            hashSet.add(strings[1]);
        }
        int x = 1;
        for (String string : hashSet) {
            HashMap<String, Double[]> hashMap = new HashMap<>();
            // 把经纬度1-2这种格式转换成1,2这样的格式
            String[] strings = string.split("-");
            Double[] ints = new Double[2];
            ints[0] = Double.parseDouble(strings[0]);
            ints[1] = Double.parseDouble(strings[1]);

            hashMap.put("驻留地" + x, ints);
            residentList.add(hashMap);
            x++;
        }
        // 置位0，不影响其他业务类
        jedis.select(0);
        System.out.println("3、驻留地数据加载完成");
    }

    // 2、工作地数据初始化
    // @Scheduled(fixedRate = 999999999)
    public void initWork() {
        // 获取Jedis对象
        Jedis jedis = JedisConnect.getJedis();
        // 默认是数据库0，调用这个函数代表切换为数据库1,之后记得重置为0
        jedis.select(1);
        // 获取所有key
        Set<String> allKey = jedis.keys("lingering:*工作地*");
        // 存储每个工作地的经纬度
        HashSet<String> hashSet = new HashSet<>();
        for (String string : allKey) {
            String[] strings = string.split(":");
            hashSet.add(strings[1]);
        }
        int x = 1;
        for (String string : hashSet) {
            HashMap<String, Double[]> hashMap = new HashMap<>();
            // 把经纬度1-2这种格式转换成1,2这样的格式
            String[] strings = string.split("-");
            Double[] ints = new Double[2];
            ints[0] = Double.parseDouble(strings[0]);
            ints[1] = Double.parseDouble(strings[1]);

            hashMap.put("工作地" + x, ints);
            workList.add(hashMap);
            x++;
        }
        // 置位0，不影响其他业务类
        jedis.select(0);
        System.out.println("4、工作地数据加载完成");
    }

    // 3、居住地数据初始化
    // @Scheduled(fixedRate = 999999999)
    public void initLive() {
        // 获取Jedis对象
        Jedis jedis = JedisConnect.getJedis();
        // 默认是数据库0，调用这个函数代表切换为数据库1,之后记得重置为0
        jedis.select(1);
        // 获取所有key
        Set<String> allKey = jedis.keys("lingering:*居住地*");
        // 存储每个居住地的经纬度
        HashSet<String> hashSet = new HashSet<>();
        for (String string : allKey) {
            String[] strings = string.split(":");
            hashSet.add(strings[1]);
        }
        int x = 1;
        for (String string : hashSet) {
            HashMap<String, Double[]> hashMap = new HashMap<>();
            // 把经纬度1-2这种格式转换成1,2这样的格式
            String[] strings = string.split("-");
            Double[] ints = new Double[2];
            ints[0] = Double.parseDouble(strings[0]);
            ints[1] = Double.parseDouble(strings[1]);

            hashMap.put("居住地" + x, ints);
            liveList.add(hashMap);
            x++;
        }
        // 置位0，不影响其他业务类
        jedis.select(0);
        System.out.println("5、居住地数据加载完成");
    }
}
