package com.mmall.util;

import com.mmall.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Sharded;

/**
 * Created by Administrator on 2018/1/17 0017.
 */
@Slf4j
public class RedisShardedPoolUtil {

    public static String set(String key,String value){
        ShardedJedis jedis = null;
        String result =null;
        try{
            jedis = RedisShardedPool.getJedis(); //获取jedis连接
            result = jedis.set(key,value);
        } catch(Exception e){
            log.error("set key : {} value : {}",key,value,e);
            RedisShardedPool.returnResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    public static String get(String key){
        ShardedJedis jedis = null;
        String result =null;
        try{
            jedis = RedisShardedPool.getJedis(); //获取jedis连接
            result = jedis.get(key);
        } catch(Exception e){
            log.error("get key : {}",key,e);
            RedisShardedPool.returnResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    //exTime单位是秒
    public static String setEx(String key,String value,int exTime){
        ShardedJedis jedis = null;
        String result =null;
        try{
            jedis = RedisShardedPool.getJedis(); //获取jedis连接
            result = jedis.setex(key,exTime,value);
        } catch(Exception e){
            log.error("set key : {} value : {}",key,value,e);
            RedisShardedPool.returnResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    /**
     * 设置key的有效期，单位是秒
     * @param key
     * @param exTime
     * @return
     */
    public static Long expire(String key,int exTime){
        ShardedJedis jedis = null;
        Long result =null;
        try{
            jedis = RedisShardedPool.getJedis(); //获取jedis连接
            result = jedis.expire(key,exTime);
        } catch(Exception e){
            log.error("expire key : {} error",key,e);
            RedisShardedPool.returnResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    /**
     * 删除jedis
     * @param key
     * @return
     */
    public static Long del(String key){
        ShardedJedis jedis = null;
        Long result =null;
        try{
            jedis = RedisShardedPool.getJedis(); //获取jedis连接
            result = jedis.del(key);
        } catch(Exception e){
            log.error("delete key : {} error",key,e);
            RedisShardedPool.returnResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }

    //redis中的setnx方法是当key不存在时才会创建
    public static Long setNx(String key,String value){
        ShardedJedis jedis = null;
        Long result =null;
        try{
            jedis = RedisShardedPool.getJedis(); //获取jedis连接
            //如果key不存在，才会设置成功
            result = jedis.setnx(key,value);
        } catch(Exception e){
            log.error("set key : {} value : {}",key,value,e);
            RedisShardedPool.returnResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }


    public static void main(String[] args) {
        //Jedis jedis = RedisPool.getJedis();

//        RedisShardedPoolUtil.set("keyTest","value");
//        String value = RedisShardedPoolUtil.get("keyTest");
//        System.out.println(value);
//
//        RedisShardedPoolUtil.setEx("keyEx","valueEx",60*10);
//
//        RedisShardedPoolUtil.expire("keyTest",60*20);
//
//        RedisShardedPoolUtil.del("keyTest");
    }

}
