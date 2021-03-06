package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by Administrator on 2018/1/17 0017.
 */
public class RedisPool {

    private static JedisPool pool; //jedis连接池

    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20")); //最大连接数

    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","10"));// 最大空闲状态jedis实例数

    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","2"));  //最小空闲状态jedis实例数

    private static boolean testOnBrrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));// 验证从pool中获取的jedis实例

    private static boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true")); // 验证放回pool中的jedis实例

    private static String redisIp = PropertiesUtil.getProperty("redis.ip");

    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));

    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBrrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true); //连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时，默认为true

        pool = new JedisPool(config,redisIp,redisPort,1000*2);
    }

    static{
        initPool(); //初始化连接池
    }

    /**
     * 获取jedis
     * @return
     */
    public static Jedis getJedis(){
        return pool.getResource();
    }

    /**
     * 退还jedis
     * @param jedis
     */
    public static void returnResource(Jedis jedis){
        pool.returnResource(jedis);
    }

    /**
     * jedis连接损坏
     * @param jedis
     */
    public static void returnBrokenResource(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        jedis.set("greelykey","greelykey");
        returnResource(jedis);

        pool.destroy(); //临时销毁连接池

        System.out.println("结束...");
    }

}
