package com.mmall.util;

import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * Created by Administrator on 2018/1/17 0017.
 */
@Slf4j
public class RedisPoolUtil {

    public static String set(String key,String value){
        Jedis jedis = null;
        String result =null;
        try{
            jedis = RedisPool.getJedis(); //获取jedis连接
            result = jedis.set(key,value);
        } catch(Exception e){
            log.error("set key : {} value : {}",key,value,e);
            RedisPool.returnResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String get(String key){
        Jedis jedis = null;
        String result =null;
        try{
            jedis = RedisPool.getJedis(); //获取jedis连接
            result = jedis.get(key);
        } catch(Exception e){
            log.error("get key : {}",key,e);
            RedisPool.returnResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    //exTime单位是秒
    public static String setEx(String key,String value,int exTime){
        Jedis jedis = null;
        String result =null;
        try{
            jedis = RedisPool.getJedis(); //获取jedis连接
            result = jedis.setex(key,exTime,value);
        } catch(Exception e){
            log.error("set key : {} value : {}",key,value,e);
            RedisPool.returnResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    /**
     * 设置key的有效期，单位是秒
     * @param key
     * @param exTime
     * @return
     */
    public static Long expire(String key,int exTime){
        Jedis jedis = null;
        Long result =null;
        try{
            jedis = RedisPool.getJedis(); //获取jedis连接
            result = jedis.expire(key,exTime);
        } catch(Exception e){
            log.error("expire key : {} error",key,e);
            RedisPool.returnResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    /**
     * 删除jedis
     * @param key
     * @return
     */
    public static Long del(String key){
        Jedis jedis = null;
        Long result =null;
        try{
            jedis = RedisPool.getJedis(); //获取jedis连接
            result = jedis.del(key);
        } catch(Exception e){
            log.error("delete key : {} error",key,e);
            RedisPool.returnResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }


    public static void main(String[] args) {
        Jedis jedis = RedisPool.getJedis();

        RedisPoolUtil.set("keyTest","value");
        String value = RedisPoolUtil.get("keyTest");
        System.out.println(value);

        RedisPoolUtil.setEx("keyEx","valueEx",60*10);

        RedisPoolUtil.expire("keyTest",60*20);

        RedisPoolUtil.del("keyTest");
    }

}
