package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * redis分片连接池
 * Created by Administrator on 2018/1/21 0021.
 */
public class RedisShardedPool {
    private static ShardedJedisPool pool; //sharded jedis连接池

    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20")); //最大连接数

    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","10"));// 最大空闲状态jedis实例数

    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","2"));  //最小空闲状态jedis实例数

    private static boolean testOnBrrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));// 验证从pool中获取的jedis实例

    private static boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true")); // 验证放回pool中的jedis实例

    private static String redisIp1 = PropertiesUtil.getProperty("redis1.ip");

    private static Integer redisPort1 = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));

    private static String redisIp2 = PropertiesUtil.getProperty("redis2.ip");

    private static Integer redisPort2 = Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));

    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBrrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true); //连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时，默认为true

        JedisShardInfo info1 = new JedisShardInfo(redisIp1,redisPort1,1000*2);
        JedisShardInfo info2 = new JedisShardInfo(redisIp2,redisPort2,1000*2);

        List<JedisShardInfo> jedisShardInfoList = new ArrayList<JedisShardInfo>();
        jedisShardInfoList.add(info1);
        jedisShardInfoList.add(info2);

        //Hashing.MURMUR_HASH对应一致性算法
        pool = new ShardedJedisPool(config,jedisShardInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    static{
        initPool(); //初始化连接池
    }

    /**
     * 获取sharded jedis
     * @return
     */
    public static ShardedJedis getJedis(){
        return pool.getResource();
    }

    /**
     * 退还jedis
     * @param jedis
     */
    public static void returnResource(ShardedJedis jedis){
        pool.returnResource(jedis);
    }

    /**
     * jedis连接损坏
     * @param jedis
     */
    public static void returnBrokenResource(ShardedJedis jedis){
        pool.returnBrokenResource(jedis);
    }

    public static void main(String[] args) {
        ShardedJedis jedis = pool.getResource();
        //jedis.set("greelykey","greelykey");

        for(int i = 0;i <10;i++){
            jedis.set("key" + i,"value"+i);
        }

        returnResource(jedis);

        //pool.destroy(); //临时销毁连接池

        System.out.println("结束...");
    }
}
