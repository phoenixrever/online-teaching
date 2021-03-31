package com.phoenixhell.serviceOrder.appBoot;

import com.phoenixhell.serviceOrder.entity.Order;
import com.phoenixhell.serviceOrder.service.OrderService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *如果我们想在项目启动后做一些事情（如加载定时任务，初始化工作），
 * 可以使用spring提供的CommandLineRunner、ApplicationRunner 接口，
 * 在容器启动成功后的最后一步回调（类似开机自启动）
 *
 */
@Component
@Data
public class DelayQueueManager implements ApplicationRunner {
    private DelayQueue<Order> delayQueue =new DelayQueue<>();
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
//    public void deleteUnPayedOrder(T t){
//        Class clazz= t.getClass();
//        try {
//            Method[] declaredMethods = clazz.getDeclaredMethods();
//            for(Method m : declaredMethods){
//                System.out.println(m);
//            }
//
//            Method deleteOrder = clazz.getDeclaredMethod("deleteUnPayedOrder", String.class);
//            deleteOrder.setAccessible(true);
//            deleteOrder.invoke(clazz,orderId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        ExecutorService threadpool = Executors.newSingleThreadExecutor();
        threadpool.execute(()-> {
            Set<String> order_delay = redisTemplate.opsForZSet().rangeByScore("order_delay", 0, Double.parseDouble(String.valueOf(System.currentTimeMillis())));
            if(order_delay!=null && order_delay.size()!=0){
                order_delay.forEach(e->{
                    String s = new String(e.getBytes());
                    System.out.println("************"+s);
                    Order order = orderService.getById(s);
                    System.out.println(order);
                    if(order!=null){
                        orderService.removeById(s);
                    }
                });
            }
            try {
                while (true) {
                    Order take = delayQueue.take();
                    orderService.removeById(take.getId());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
