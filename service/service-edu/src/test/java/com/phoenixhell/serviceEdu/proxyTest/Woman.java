package com.phoenixhell.serviceEdu.proxyTest;

/**
 * @author phoenixhell
 * @since 2021/3/6 0006-下午 4:02
 */

abstract class Woman implements Human{
    @Override
    public void eat(String food) {
        String fuck = fuck(food);
        System.out.println("woman eat" +fuck);
    }
    abstract String fuck(String food);
}
