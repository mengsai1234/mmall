package com.mmall.task;

import com.mmall.common.Const;
import com.mmall.common.RedisShardedPool;
import com.mmall.common.RedissonManager;
import com.mmall.service.IOrderService;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 定时关闭订单
 * 到达指定时间后，未付款订单将被关闭
 * Created by Administrator on 2018/1/23 0023.
 */
@Component
@Slf4j
public class CloseOrderTask {

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private RedissonManager redissonManager;

    //cron="* */1 * * * ?" 秒 分 时 日 月 周
    //@Scheduled(cron = "* */1 * * * ?") //当时间是1分钟的整数倍时执行该方法
    public void closeOrderTaskV1(){

        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour","2"));
        log.info("关闭订单任务启动");
        //每一分钟执行一次任务，关闭从下订单时间到超过两小时未付款的订单
        //关闭以当前时间为准，两个小时之前下单但是未付款的订单
        iOrderService.closeOrder(hour);
        log.info("关闭订单任务结束");
    }

    @Scheduled(cron = "* */1 * * * ?") //当时间是1分钟的整数倍时执行该方法
    public void closeOrderTaskV2(){
        log.info("关闭订单任务启动");

        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout"));
        Long setnxResult = RedisShardedPoolUtil.setNx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,String.valueOf(System.currentTimeMillis()+lockTimeout));

        //如果返回值为1，则设置锁成功
        if(setnxResult != null || setnxResult == 1){
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }else{
            log.info("没有获得分布式锁:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }

        log.info("关闭订单任务结束");
    }

    //设置锁的有效期
    private void closeOrder(String lockName){
        RedisShardedPoolUtil.expire(lockName,50); //设置有效期为50秒，防止死锁
        log.info("获取:{},ThreadName:{}",lockName,Thread.currentThread().getName());
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour","2"));
        iOrderService.closeOrder(hour);

        //释放锁
        RedisShardedPoolUtil.del(lockName);
        log.info("释放:{},ThreadName:{}",lockName,Thread.currentThread().getName());
        log.info("===========================");
    }

    //Redisson实现
    @Scheduled(cron = "* */1 * * * ?") //当时间是1分钟的整数倍时执行该方法
    public void closeOrderTaskV4(){
        RLock lock = redissonManager.getRedisson().getLock(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        boolean getLock = false;
        try {
            //获取等待时间2秒，锁持有时间5秒，单位是秒
            //可以设置waitTime时间为0，这样在多个tomcat竞争锁的时候，不需要等待时间
            if(lock.tryLock(2,5, TimeUnit.SECONDS)){
                log.info("Redisson获取分布式锁:{},ThreadName:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread().getName());
                getLock = true;
                int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour","2"));
                iOrderService.closeOrder(hour); //关闭订单
            }else{
                log.info("Redisson没有获取分布式锁:{},ThreadName:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            log.error("Redisson分布式锁获取异常",e);
        } finally{
            if(!getLock){
                return;
            }
            lock.unlock(); //释放锁
            log.info("Redisson分布式锁释放");
        }

    }

}
