package me.bayes.crashub.agent;

import java.util.concurrent.CountDownLatch;

/**
 * Created by kevinbayes on 2015/10/14.
 */
public class Launcher {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(10);

        System.out.println("Welcome");


        latch.await();
    }
}
