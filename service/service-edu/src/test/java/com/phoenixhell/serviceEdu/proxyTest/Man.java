package com.phoenixhell.serviceEdu.proxyTest;

/**
 * @author phoenixhell
 * @since 2021/3/6 0006-下午 2:42
 */

class Man implements Human {
    @Override
    public void eat(String food) {
        System.out.println("man eat "+food);
    }
}
