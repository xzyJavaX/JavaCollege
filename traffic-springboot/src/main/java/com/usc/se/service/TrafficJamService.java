package com.usc.se.service;


import com.usc.se.util.JamTime;
import com.usc.se.util.JedisConnect;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * 1、交通拥挤情况
 */
@Service
public class TrafficJamService {
    // 用来存0-24小时的数据
    static HashMap<Integer, ArrayList<Object>> resultHashMap = new HashMap<>();

    /**
     * 从Redis中查询0-1点的交通拥挤数据
     */
    public ArrayList<Object> get() {
        // 初始化时间
        int time = JamTime.init();
        return getByTime(time);

    }

    /**
     * 获取后一个小时的交通拥挤数据
     */
    public ArrayList<Object> getNext() {
        int time = JamTime.add();
        if (time != 24) {
            return getByTime(time);
        }
        time = JamTime.init();
        return getByTime(time);
    }

    /**
     * 获取前一个小时的交通拥挤数据
     */
    public ArrayList<Object> getPrior(){
        int time = JamTime.reduce();
        if (time != -1) {
            return getByTime(time);
        }
        time = JamTime.last();
        return getByTime(time);
    }

    // 初始化，把数据放入内存
    // @Scheduled(fixedRate = 999999999)
    public void init() {
        for (int i = 0; i < 24; i++) {
            resultHashMap.put(i,storeByTime(i));
        }
        System.out.println("1、交通拥挤情况初始化完成");
    }

    // 根据传入的时间，把对应的数据存入HashMap
    public ArrayList<Object> storeByTime(int time) {
        // 时间，以及各个站台的情况，初始值1000，减少扩容次数
        ArrayList<Object> arrayList = new ArrayList<>(1000);
        // 根据传入的time，拼接得到对应的key，例如：crowd:10-11*
        StringBuilder stringBuilder = new StringBuilder("crowd:");
        if (time <= 8) {
            stringBuilder.append("0"+ time + "-" + "0" + (time + 1) + "*");
            String string = "0"+ time + "-" + "0" + (time + 1);
            arrayList.add(string);
        } else if (time == 9) {
            stringBuilder.append("0"+ time + "-" + (time + 1) + "*");
            String string = "0"+ time + "-" + (time + 1);
            arrayList.add(string);
        } else if (time > 9 && time < 23){
            stringBuilder.append(time + "-" + (time + 1) + "*");
            String string = time + "-" + (time + 1);
            arrayList.add(string);
        } else if (time == 23){
            stringBuilder.append("23-00*");
            String string = "23-00";
            arrayList.add(string);
        }
        // 获取Jedis对象
        Jedis jedis = JedisConnect.getJedis();
        // 获取满足条件的key
        Set<String> keySet = jedis.keys(stringBuilder.toString());
        // 站点x
        int x = 1;
        for (String key : keySet) {
            HashMap<String, Object> hashMap2 = new HashMap<>();
            // 先根据key获取到value，value是拥挤情况-速度
            String string = jedis.get(key);
            String[] strings2 = string.split("-");

            // 1、存入拥挤情况,规定为state
            hashMap2.put("state", strings2[0]);
            // 根据":"拆分key
            String[] strings = key.split(":");
            // 2、存入站名,规定为name
            hashMap2.put("name", strings[2]);
            String[] strings1 = strings[3].split("-");
            // 存放经纬度
            ArrayList<Double> arrayList1 = new ArrayList<>();
            arrayList1.add(Double.parseDouble(strings1[0]));
            arrayList1.add(Double.parseDouble(strings1[1]));
            // 3、存入经纬度,规定为站点x
            hashMap2.put("站点" + x, arrayList1);
            x++;
            arrayList.add(hashMap2);
        }
        return arrayList;
    }

    /**
     * 根据传入的time，获取对应的数据,time的范围是[0,23]
     */
    public ArrayList<Object> getByTime(int time) {
        return resultHashMap.get(time);
    }

}
