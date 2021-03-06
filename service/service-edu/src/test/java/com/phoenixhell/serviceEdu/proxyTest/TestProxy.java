package com.phoenixhell.serviceEdu.proxyTest;

/**
 * @author phoenixhell
 * @since 2021/3/6 0006-下午 3:27
 */

public class TestProxy extends ManProxy {

    @Override
    String before(String args) {
        args = "我改变了参数";
        return args;
    }

    public static void main(String[] args) {
        TestProxy testProxy = new TestProxy();
        Human proxyInstance = (Human) testProxy.getProxyInstance(new Man());
        proxyInstance.eat("shit");
    }
}
