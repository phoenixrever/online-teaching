package com.phoenixhell.serviceOrder.appBoot;

import com.phoenixhell.serviceOrder.entity.Order;
import com.phoenixhell.serviceOrder.service.OrderService;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
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
