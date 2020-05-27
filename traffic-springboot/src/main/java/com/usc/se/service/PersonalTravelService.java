package com.usc.se.service;

import com.usc.se.util.JedisConnect;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * 4、出行方式分析
 */
@Service
public class PersonalTravelService {
    // 1、总出行情况
    static ArrayList<HashMap<String,Object>> allList = new ArrayList<>();

    // 2、夜行出行情况
    static ArrayList<HashMap<String,Object>> nightList = new ArrayList<>();

    // 1、返回总出行情况
    public ArrayList<HashMap<String,Object>> getAll() {
        return allList;
    }

    // 2、返回夜行出行情况
    public ArrayList<HashMap<String,Object>> getNight() {
        return nightList;
    }

    // 全部出行方式初始化
    // @Scheduled(fixedRate = 999999999)
    public void initAll() {
        // 获取Jedis对象
        Jedis jedis = JedisConnect.getJedis();
        // 使用DB2
        jedis.select(2);
        Set<String> allKey = jedis.keys("trip:*");
        for (String string : allKey) {
            HashMap<String, Object> hashMap = new HashMap<>();
            String id = string.substring(5,23);
            // 1、id
            hashMap.put("id",id);

            // 根据key获取value
            String data = jedis.get(string);
            String[] datas = data.split(":");

            // 时间，格式："18:33"
            ArrayList<String> times = new ArrayList<>();
            // 经纬度，格式：[123123,123123]
            ArrayList<Double[]> paths = new ArrayList<>();
            // 当前出行方式，格式："公交车"
            ArrayList<String> states = new ArrayList<>();
            // 速度，格式：12.123
            ArrayList<Double> speeds = new ArrayList<>();

            // 每一个data1的格式是这样的：20181003225759-123.4172211-41.8232193-公交车-4.3279
            // 遍历value，填充4个ArrayList的数据
            for (String data1 : datas) {
                String[] datas1 = data1.split("-");

                // 把时间处理成对应的格式,并加入ArrayList
                StringBuilder time = new StringBuilder(datas1[0].substring(8,12));
                time.insert(2,":");
                times.add(time.toString());

                // 把经纬度处理成对应的格式,并加入ArrayList
                Double[] path = new Double[2];
                path[0] = Double.parseDouble(datas1[1]);
                path[1] = Double.parseDouble(datas1[2]);
                paths.add(path);

                // 出行方式
                states.add(datas1[3]);
                // 速度
                speeds.add(Double.parseDouble(datas1[4]));
            }
            // 2、time
            hashMap.put("time",times);
            // 3、path
            hashMap.put("path",paths);
            // 4、state
            hashMap.put("state",states);
            // 5、speed
            hashMap.put("speed",speeds);

            // 添加到最后要输出的allList中
            allList.add(hashMap);
        }
        // 用完就重新把DB置为0
        jedis.select(0);
        System.out.println("6、全部出行方式已加载完成");
    }

    // 夜行出行方式初始化
    // @Scheduled(fixedRate = 999999999)
    public void initNight() {
        // 获取Jedis对象
        Jedis jedis = JedisConnect.getJedis();
        // 使用DB2
        jedis.select(2);
        Set<String> allKey = jedis.keys("trip:*夜行*");
        for (String string : allKey) {
            HashMap<String, Object> hashMap = new HashMap<>();
            String id = string.substring(5,23);
            // 1、id
            hashMap.put("id",id);

            // 根据key获取value
            String data = jedis.get(string);
            String[] datas = data.split(":");

            // 时间，格式："18:33"
            ArrayList<String> times = new ArrayList<>();
            // 经纬度，格式：[123123,123123]
            ArrayList<Double[]> paths = new ArrayList<>();
            // 当前出行方式，格式："公交车"
            ArrayList<String> states = new ArrayList<>();
            // 速度，格式：12.123
            ArrayList<Double> speeds = new ArrayList<>();

            // 每一个data1的格式是这样的：20181003225759-123.4172211-41.8232193-公交车-4.3279
            // 遍历value，填充4个ArrayList的数据
            for (String data1 : datas) {
                String[] datas1 = data1.split("-");

                // 把时间处理成对应的格式,并加入ArrayList
                StringBuilder time = new StringBuilder(datas1[0].substring(8,12));
                time.insert(2,":");
                times.add(time.toString());

                // 把经纬度处理成对应的格式,并加入ArrayList
                Double[] path = new Double[2];
                path[0] = Double.parseDouble(datas1[1]);
                path[1] = Double.parseDouble(datas1[2]);
                paths.add(path);

                // 出行方式
                states.add(datas1[3]);
                // 速度
                speeds.add(Double.parseDouble(datas1[4]));
            }
            // 2、time
            hashMap.put("time",times);
            // 3、path
            hashMap.put("path",paths);
            // 4、state
            hashMap.put("state",states);
            // 5、speed
            hashMap.put("speed",speeds);

            // 添加到最后要输出的allList中
            nightList.add(hashMap);
        }
        // 用完就重新把DB置为0
        jedis.select(0);
        System.out.println("7、夜行出行方式已加载完成");
    }
}
