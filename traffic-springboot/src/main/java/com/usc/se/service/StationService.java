package com.usc.se.service;

import com.usc.se.util.JedisConnect;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * 2、车站一天的情况
 */
@Service
public class StationService {

    // 一天，968个车站的全部信息
    static ArrayList<Object> arrayList = new ArrayList<>(1024);

    // 一天,968个车站，一天的平均速度数组
    static HashMap<String, Double[]> vMap = new HashMap<>(2048);

    // 直接返回加载好的数据
    public ArrayList<Object> getStation() {
        return arrayList;
    }

    public Double[] getSpeedByName(String name) {
        return vMap.get(name);
    }


    // 启动时加载数据
    // @Scheduled(fixedRate = 999999999)
    public void init() {
        // 获取Jedis对象
        Jedis jedis = JedisConnect.getJedis();
        // 获取所有key
        Set<String> allKey = jedis.keys("crowd*");

        // 用来存放每个车站24个小时的情况,初始大小2048，减少扩容次数
        HashMap<String, String[]> stationMap = new HashMap<>(2048);
        for (String allkey : allKey) {

            // 得到该key的值
            String value = jedis.get(allkey);
            // 切割，values[0]即为交通拥挤情况
            String values[] = value.split("-");

            String[] allData = allkey.split(":");
            // 如果已经包含该车站，添加交通拥挤情况即可
            if (stationMap.containsKey(allData[2])) {
                // 计算该交通情况该存入的位置
                int index = (allData[1].charAt(0) - 48 ) * 10 + allData[1].charAt(1) - 48;
                stationMap.get(allData[2])[index] = values[0];
            } else {
                stationMap.put(allData[2],new String[24]);
            }
        }

        for (Map.Entry<String, String[]> entry : stationMap.entrySet()) {
            HashMap<String, Object> resultHashMap = new HashMap<>();
            resultHashMap.put("name",entry.getKey());
            resultHashMap.put("condition",entry.getValue());
            arrayList.add(resultHashMap);
        }
        System.out.println("2、各个车站一天的情况初始化完成");
    }

    // 加载各个车站一天的速度
    public void initV() {
        // 获取Jedis对象
        Jedis jedis = JedisConnect.getJedis();
        // 获取所有key
        Set<String> allKey = jedis.keys("crowd*");

        for (String allkey : allKey) {
            // 得到该key的值
            String value = jedis.get(allkey);

            // 切割，values[1]即为该时段的平均速度
            String values[] = value.split("-");

            String[] allData = allkey.split(":");
            // 如果已经包含该车站，添加交通拥挤情况即可
            if (vMap.containsKey(allData[2])) {
                // 计算该交通情况该存入的位置
                int index = (allData[1].charAt(0) - 48 ) * 10 + allData[1].charAt(1) - 48;
                vMap.get(allData[2])[index] = Double.parseDouble(values[1]);
            } else {
                Double[] doubles = new Double[24];
                for (int i = 0; i < 24; i++) {
                    doubles[i] = 8.4;
                }
                vMap.put(allData[2],doubles);
            }
        }
        System.out.println("8、各个车站一天的平均速度初始化完成");
    }
}
