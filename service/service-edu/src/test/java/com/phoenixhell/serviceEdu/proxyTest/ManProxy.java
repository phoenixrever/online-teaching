package com.phoenixhell.serviceEdu.proxyTest;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author phoenixhell
 * @since 2021/3/6 0006-下午 3:14
 */

public abstract class ManProxy {
    public  Object getProxyInstance(Object o){
        return Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before---------------------");
                String before = before((String) args[0]);
                System.out.println(before);
                Object invoke = method.invoke(o, before);
                //eat没有返回值 所以null
                System.out.println(invoke);
                return invoke;
            }
        });
    }
 abstract String before(String args);
}
