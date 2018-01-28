package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2018/1/28 0028.
 */
@Component
@Slf4j
public class RedissonManager {

    private Config config = new Config();

    @Getter
    private Redisson redisson = null;

    private static String redisIp1 = PropertiesUtil.getProperty("redis1.ip");
    private static Integer redisPort1 = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));
    private static String redisIp2 = PropertiesUtil.getProperty("redis2.ip");
    private static Integer redisPort2 = Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));

    @PostConstruct //在构造器完成后自动执行该方法
    private void init(){
        try {
            //单服务
            config.useSingleServer().setAddress(new StringBuilder().append(redisIp1).append(":").append(redisPort1).toString());
            redisson = (Redisson) Redisson.create(config);
            log.info("初始化redisson结束");
        } catch (Exception e) {
            log.error("redisson init error",e);
        }

    }

}
