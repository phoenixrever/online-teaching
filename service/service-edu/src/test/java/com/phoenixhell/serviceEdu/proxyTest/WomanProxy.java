package com.phoenixhell.serviceEdu.proxyTest;

/**
 * @author phoenixhell
 * @since 2021/3/6 0006-下午 4:04
 */

public class WomanProxy extends Woman {
    @Override
    String fuck(String food) {
        return "food is not good";
    }

    public static void main(String[] args) {
        Human Human = new WomanProxy();
        Human.eat("ssss");
    }
}
