package com.phoenixhell.serviceVod.util;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author phoenixhell
 * @since 2021/3/17 0017-上午 10:41
 */
@Data
public class ProgressBar {
    private static ProgressBar progressBar =new ProgressBar();
    private volatile static Map<String,Object> percentMap=new HashMap<>();
    private AtomicInteger atomicInteger=new AtomicInteger();

    private ProgressBar(){
    }
    static {
        percentMap.put("admin", "0");
    }

    public static Map<String, Object> getPercentMap() {
        return percentMap;
    }

    public Integer getAtomicInteger() {
        return atomicInteger.get();
    }

    public void setAtomicInteger(Integer integer) {
        this.atomicInteger.set(integer);
    }
    //    AtomicReference<Thread> atomicReference =new AtomicReference<>();
//    void myLock(){
//        Thread thread =Thread.currentThread();
//
//        while(!atomicReference.compareAndSet(null, thread)){}
//    }
//    void unLock(){
//        atomicReference.compareAndSet(Thread.currentThread(), null);
//    }

}
